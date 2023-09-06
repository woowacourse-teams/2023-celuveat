package com.celuveat.restaurant.query.dao;

import static com.celuveat.celeb.command.domain.QCeleb.celeb;
import static com.celuveat.common.util.StringUtil.removeAllBlank;
import static com.celuveat.restaurant.command.domain.QRestaurant.restaurant;
import static com.celuveat.restaurant.command.domain.QRestaurantLike.restaurantLike;
import static com.celuveat.video.command.domain.QVideo.video;
import static com.querydsl.core.types.Order.DESC;

import com.celuveat.common.query.DynamicQuery;
import com.celuveat.common.query.DynamicQueryAssembler;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.exception.RestaurantException;
import com.celuveat.restaurant.exception.RestaurantExceptionType;
import com.celuveat.restaurant.query.dto.RestaurantWithDistance;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
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

    private final JPAQueryFactory query;

    private static final String HAVERSINE_FORMULA = """
            (6371 * acos(cos(radians(%s)) * cos(radians(latitude))
                 * cos(radians(longitude) - radians(%s))
                 + sin(radians(%s)) * sin(radians(latitude)))) * 1000
             """;

    private NumberExpression<Double> distance(double latitude, double longitude) {
        return Expressions.numberTemplate(Double.class,
                """
                        6371 * acos(cos(radians({1})) * cos(radians({0}.latitude))
                        * cos(radians({0}.longitude) - radians({2}))
                        + sin(radians({1})) * sin(radians({0}.latitude))) * 1000
                        """,
                restaurant, latitude, longitude);
    }

    private static final String ORDER_BY_DISTANCE_ASC = """
            ORDER BY dist ASC
            """;

    //FIXME
    private static final String SELECT_RESTAURANT_NEARBY_SPECIFIC_DISTANCE = """
            SELECT new com.celuveat.restaurant.query.dto.RestaurantWithDistance(
                r.id,
                r.name,
                r.category,
                r.roadAddress,
                r.latitude,
                r.longitude,
                r.phoneNumber,
                r.naverMapUrl,
                r.viewCount,
                %s AS dist
            )
            FROM Restaurant r
            """;

    private static final String DISTANCE_MIN_OR_EQUAL = """
            %s <= %d
            """;

    private static final String RESTAURANT_ID_NOT_EQUAL = """
            r.id != %d
            """;

    private static final String COUNT_QUERY_NEARBY_DISTANCE = """
            SELECT count(r)
            FROM Restaurant r
            """;

    private final EntityManager em;

    private static final NumberPath<Double> distance = Expressions.numberPath(Double.class, "distance");

    public Page<RestaurantWithDistance> search(
            RestaurantSearchCond restaurantSearchCond,
            LocationSearchCond locationSearchCond,
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
                        distance(middleLat, middleLng).as(distance)
                ))
                .from(restaurant)
                .join(video).on(video.restaurant.eq(restaurant))
                .join(celeb).on(celeb.eq(video.celeb))
                .where(
                        celebIdEqual(restaurantSearchCond.celebId),
                        restaurantCategoryEqual(restaurantSearchCond.category),
                        restaurantNameLike(restaurantSearchCond.restaurantName),
                        restaurantInArea(locationSearchCond)
                ).orderBy(applyOrderBy(pageable))
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
        String sortProperty = pageable.getSort().stream()
                .map(Order::getProperty)
                .findFirst()
                .orElse(RestaurantSortType.DISTANCE.sortProperty);
        RestaurantSortType sortType = RestaurantSortType.from(sortProperty);
        if (sortType == RestaurantSortType.LIKE_COUNT) {
            SubQueryExpression<Long> orderByLikeDesc = JPAExpressions
                    .select(restaurantLike.count())
                    .from(restaurantLike)
                    .where(restaurantLike.restaurant.eq(restaurant));
            return new OrderSpecifier<>(DESC, orderByLikeDesc);
        }
        return distance.asc();
    }

    private double calculateMiddle(double x, double y) {
        return (x + y) / 2.0;
    }

    private String getDistanceColumn(double middleLat, double middleLng) {
        return HAVERSINE_FORMULA.formatted(middleLat, middleLng, middleLat);
    }

    public Page<RestaurantWithDistance> searchNearBy(
            Long restaurantId,
            int distance,
            Pageable pageable
    ) {
        Restaurant restaurant = em.find(Restaurant.class, restaurantId);
        String dist = getDistanceColumn(restaurant.latitude(), restaurant.longitude());
        String whereQuery = DynamicQueryAssembler.assemble(
                distanceMinOrEqual(dist, distance),
                restaurantIdNotEqual(restaurant.id())
        );
        List<RestaurantWithDistance> result = em.createQuery(
                        SELECT_RESTAURANT_NEARBY_SPECIFIC_DISTANCE.formatted(dist)
                                + whereQuery
                                + ORDER_BY_DISTANCE_ASC,
                        RestaurantWithDistance.class
                )
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return PageableExecutionUtils.getPage(
                result,
                pageable,
                () -> (Long) em.createQuery(COUNT_QUERY_NEARBY_DISTANCE + whereQuery).getSingleResult()
        );
    }

    private DynamicQuery distanceMinOrEqual(String distColumn, int distance) {
        return DynamicQuery.builder()
                .query(DISTANCE_MIN_OR_EQUAL)
                .params(distColumn, distance)
                .condition(true)
                .build();
    }

    private DynamicQuery restaurantIdNotEqual(Long id) {
        return DynamicQuery.builder()
                .query(RESTAURANT_ID_NOT_EQUAL)
                .params(id)
                .condition(true)
                .build();
    }

    //FIXME
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
