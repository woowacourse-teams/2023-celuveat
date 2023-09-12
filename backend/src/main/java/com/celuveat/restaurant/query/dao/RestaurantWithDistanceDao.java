package com.celuveat.restaurant.query.dao;

import static com.celuveat.celeb.command.domain.QCeleb.celeb;
import static com.celuveat.common.util.StringUtil.removeAllBlank;
import static com.celuveat.restaurant.command.domain.QRestaurant.restaurant;
import static com.celuveat.restaurant.command.domain.QRestaurantLike.restaurantLike;
import static com.celuveat.video.command.domain.QVideo.video;
import static com.querydsl.core.types.dsl.Expressions.as;
import static com.querydsl.jpa.JPAExpressions.select;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.exception.RestaurantException;
import com.celuveat.restaurant.exception.RestaurantExceptionType;
import com.celuveat.restaurant.query.dao.support.RestaurantQueryDaoSupport;
import com.celuveat.restaurant.query.dto.RestaurantWithDistance;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantWithDistanceDao {

    private static final NumberPath<Double> distanceColumn = Expressions.numberPath(Double.class, "distance");
    private static final NumberPath<Long> likeCountColumn = Expressions.numberPath(Long.class, "likeCount");

    private final JPAQueryFactory query;
    private final RestaurantQueryDaoSupport restaurantQueryDaoSupport;

    private NumberExpression<Double> distance(double latitude, double longitude) {
        return Expressions.numberTemplate(Double.class,
                """
                        6371 * acos(cos(radians({1})) * cos(radians({0}.latitude))
                        * cos(radians({0}.longitude) - radians({2}))
                        + sin(radians({1})) * sin(radians({0}.latitude))) * 1000
                        """,
                restaurant, latitude, longitude);
    }

    public Page<RestaurantWithDistance> search(
            RestaurantSearchCond restaurantSearchCond,
            LocationSearchCond locationSearchCond,
            @Nullable Long memberId,
            Pageable pageable
    ) {
        double middleLat = calculateMiddle(locationSearchCond.lowLatitude, locationSearchCond.highLatitude);
        double middleLng = calculateMiddle(locationSearchCond.lowLongitude, locationSearchCond.highLongitude);
        List<RestaurantWithDistance> resultList = query.selectDistinct(Projections.constructor(
                        RestaurantWithDistance.class,
                        restaurant.id,
                        restaurant.name,
                        restaurant.category,
                        restaurant.roadAddress,
                        restaurant.latitude,
                        restaurant.longitude,
                        restaurant.phoneNumber,
                        restaurant.naverMapUrl,
                        restaurant.viewCount,
                        distance(middleLat, middleLng).as(distanceColumn),
                        (as(select(restaurantLike.count())
                                .from(restaurantLike)
                                .where(restaurantLike.restaurant.id.eq(restaurant.id))
                                .groupBy(restaurant.id), likeCountColumn)),
                        (select(isLike(memberId))
                                .from(restaurantLike)
                                .where(restaurantLike.restaurant.id.eq(restaurant.id)
                                        .and(restaurantLikeMemberIdEqual(memberId)))
                                .groupBy(restaurantLike.restaurant.id))
                ))
                .from(restaurant)
                .join(video).on(video.restaurant.eq(restaurant))
                .join(celeb).on(celeb.eq(video.celeb))
                .where(
                        celebIdEqual(restaurantSearchCond.celebId),
                        restaurantCategoryEqual(restaurantSearchCond.category),
                        restaurantNameLike(restaurantSearchCond.restaurantName),
                        restaurantInArea(locationSearchCond)
                ).orderBy(applyOrderBy(pageable), restaurant.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query.select(restaurant.countDistinct())
                .from(restaurant)
                .join(video).on(video.restaurant.eq(restaurant))
                .join(celeb).on(celeb.eq(video.celeb))
                .where(
                        celebIdEqual(restaurantSearchCond.celebId),
                        restaurantCategoryEqual(restaurantSearchCond.category),
                        restaurantNameLike(restaurantSearchCond.restaurantName),
                        restaurantInArea(locationSearchCond)
                );

        return PageableExecutionUtils.getPage(resultList, pageable, countQuery::fetchOne);
    }

    private double calculateMiddle(double x, double y) {
        return (x + y) / 2.0;
    }

    private BooleanExpression celebIdEqual(Long celebId) {
        if (celebId == null) {
            return null;
        }
        return celeb.id.eq(celebId);
    }

    private BooleanExpression restaurantCategoryEqual(String category) {
        if (StringUtils.isBlank(category)) {
            return null;
        }
        return restaurant.category.eq(category);
    }

    private BooleanExpression restaurantNameLike(String restaurantName) {
        if (StringUtils.isBlank(restaurantName)) {
            return null;
        }
        return restaurant.name.contains(removeAllBlank(restaurantName));
    }

    private BooleanExpression restaurantInArea(LocationSearchCond locationSearchCond) {
        // TODO null 처리
        return restaurant.latitude.between(locationSearchCond.lowLatitude, locationSearchCond.highLatitude)
                .and(restaurant.longitude.between(locationSearchCond.lowLongitude, locationSearchCond.highLongitude));
    }

    private OrderSpecifier<?> applyOrderBy(Pageable pageable) {
        RestaurantSortType sortType = pageable.getSort().stream()
                .map(Order::getProperty)
                .findFirst()
                .map(RestaurantSortType::from)
                .orElse(RestaurantSortType.DISTANCE);
        if (sortType == RestaurantSortType.LIKE_COUNT) {
            return likeCountColumn.desc();
        }
        return distanceColumn.asc();
    }

    public Page<RestaurantWithDistance> searchNearBy(
            Long restaurantId,
            int distance,
            Long memberId,
            Pageable pageable
    ) {
        Restaurant standard = restaurantQueryDaoSupport.getById(restaurantId);
        List<RestaurantWithDistance> result = query.select(Projections.constructor(RestaurantWithDistance.class,
                        restaurant.id,
                        restaurant.name,
                        restaurant.category,
                        restaurant.roadAddress,
                        restaurant.latitude,
                        restaurant.longitude,
                        restaurant.phoneNumber,
                        restaurant.naverMapUrl,
                        restaurant.viewCount,
                        distance(standard.latitude(), standard.longitude()).as(RestaurantWithDistanceDao.distanceColumn),
                        select(restaurantLike.count())
                                .from(restaurantLike)
                                .where(restaurantLike.restaurant.id.eq(restaurant.id))
                                .groupBy(restaurant.id),
                        select(isLike(memberId))
                                .from(restaurantLike)
                                .where(restaurantLike.restaurant.id.eq(restaurant.id)
                                        .and(restaurantLikeMemberIdEqual(memberId)))
                                .groupBy(restaurantLike.restaurant.id)
                ))
                .from(restaurant)
                .where(
                        distanceMinOrEqual(standard, distance),
                        restaurantIdNotEqual(restaurantId)
                ).orderBy(RestaurantWithDistanceDao.distanceColumn.asc(), restaurant.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query.select(restaurant.count())
                .from(restaurant)
                .where(
                        distanceMinOrEqual(standard, distance),
                        restaurantIdNotEqual(restaurantId)
                );

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private BooleanExpression restaurantLikeMemberIdEqual(Long memberId) {
        if (memberId == null) {
            return null;
        }
        return restaurantLike.member.id.eq(memberId);
    }

    private BooleanExpression isLike(Long memberId) {
        if (memberId == null) {
            return Expressions.asBoolean(false);
        }
        return restaurantLike.count().gt(0);
    }

    private BooleanExpression distanceMinOrEqual(Restaurant standard, int distance) {
        return distance(standard.latitude(), standard.longitude()).loe(distance);
    }

    private BooleanExpression restaurantIdNotEqual(Long id) {
        return restaurant.id.ne(id);
    }

    @RequiredArgsConstructor
    public enum RestaurantSortType {

        DISTANCE("distance"),
        LIKE_COUNT("like"),
        ;

        private final String sortProperty;

        public static RestaurantSortType from(String sortProperty) {
            return Arrays.stream(RestaurantSortType.values())
                    .filter(type -> type.sortProperty.equals(sortProperty))
                    .findFirst()
                    .orElseThrow(() -> new RestaurantException(RestaurantExceptionType.UNSUPPORTED_SORT_PROPERTY));
        }
    }

    public record RestaurantSearchCond(
            Long celebId,
            String category,
            String restaurantName
    ) {
    }

    public record LocationSearchCond(
            Double lowLatitude,
            Double highLatitude,
            Double lowLongitude,
            Double highLongitude
    ) {
    }
}
