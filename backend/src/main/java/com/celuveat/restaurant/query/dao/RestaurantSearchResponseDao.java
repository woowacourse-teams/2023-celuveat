package com.celuveat.restaurant.query.dao;

import static com.celuveat.celeb.command.domain.QCeleb.celeb;
import static com.celuveat.common.util.StreamUtil.groupBySameOrder;
import static com.celuveat.common.util.StringUtil.removeAllBlank;
import static com.celuveat.restaurant.command.domain.QRestaurant.restaurant;
import static com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.RestaurantSortType.LIKE_COUNT;
import static com.celuveat.video.command.domain.QVideo.video;
import static com.querydsl.core.types.dsl.Expressions.numberTemplate;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.common.domain.BaseEntity;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.exception.RestaurantException;
import com.celuveat.restaurant.exception.RestaurantExceptionType;
import com.celuveat.restaurant.query.dao.support.RestaurantImageQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantLikeQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantQueryDaoSupport;
import com.celuveat.restaurant.query.dto.CelebQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantImageQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchResponse;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.query.dao.VideoQueryDaoSupport;
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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.LongSupplier;
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
public class RestaurantSearchResponseDao {

    private static final NumberPath<Double> distanceColumn = Expressions.numberPath(Double.class, "distance");

    private final JPAQueryFactory query;
    private final VideoQueryDaoSupport videoQueryDaoSupport;
    private final RestaurantQueryDaoSupport restaurantQueryDaoSupport;
    private final RestaurantLikeQueryDaoSupport restaurantLikeQueryDaoSupport;
    private final RestaurantImageQueryDaoSupport restaurantImageQueryDaoSupport;

    public Page<RestaurantSearchResponse> findAll(
            RestaurantSearchCond restaurantCond,
            LocationSearchCond locationCond,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        List<RestaurantSearchResponse> resultList = findRestaurants(restaurantCond, locationCond, pageable);
        LongSupplier totalCountSupplier = totalCountSupplier(restaurantCond, locationCond);
        Page<RestaurantSearchResponse> restaurants = PageableExecutionUtils.getPage(
                resultList, pageable, totalCountSupplier
        );
        settingCelebAndImageAndLiked(memberId, restaurants);
        return restaurants;
    }

    private List<RestaurantSearchResponse> findRestaurants(
            RestaurantSearchCond restaurantCond,
            LocationSearchCond locationCond,
            Pageable pageable
    ) {
        return query.selectDistinct(Projections.constructor(
                        RestaurantSearchResponse.class,
                        restaurant.id,
                        restaurant.name,
                        restaurant.category,
                        restaurant.roadAddress,
                        restaurant.latitude,
                        restaurant.longitude,
                        restaurant.phoneNumber,
                        restaurant.naverMapUrl,
                        restaurant.viewCount,
                        distance(locationCond.middleLat(), locationCond.middleLng()).as(distanceColumn),
                        restaurant.likeCount
                ))
                .from(restaurant)
                .join(video).on(video.restaurant.eq(restaurant))
                .join(celeb).on(celeb.eq(video.celeb))
                .where(
                        celebIdEqual(restaurantCond.celebId),
                        restaurantCategoryEqual(restaurantCond.category),
                        restaurantNameLike(restaurantCond.restaurantName),
                        restaurantInArea(locationCond)
                ).orderBy(applyOrderBy(pageable), restaurant.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private NumberExpression<Double> distance(double latitude, double longitude) {
        return numberTemplate(Double.class,
                """
                        6371 * acos(cos(radians({1})) * cos(radians({0}.latitude))
                        * cos(radians({0}.longitude) - radians({2}))
                        + sin(radians({1})) * sin(radians({0}.latitude))) * 1000
                        """,
                restaurant, latitude, longitude);
    }

    private BooleanExpression celebIdEqual(Long celebId) {
        return celebId == null ? null : celeb.id.eq(celebId);
    }

    private BooleanExpression restaurantCategoryEqual(String category) {
        return StringUtils.isBlank(category) ? null : restaurant.category.eq(category);
    }

    private BooleanExpression restaurantNameLike(String restaurantName) {
        return StringUtils.isBlank(restaurantName) ? null : restaurant.name.contains(removeAllBlank(restaurantName));
    }

    private BooleanExpression restaurantInArea(LocationSearchCond locationSearchCond) {
        return restaurant.latitude.between(locationSearchCond.lowLatitude, locationSearchCond.highLatitude)
                .and(restaurant.longitude.between(locationSearchCond.lowLongitude, locationSearchCond.highLongitude));
    }

    private OrderSpecifier<?> applyOrderBy(Pageable pageable) {
        RestaurantSortType sortType = pageable.getSort().stream()
                .map(Order::getProperty)
                .findFirst()
                .map(RestaurantSortType::from)
                .orElse(RestaurantSortType.DISTANCE);
        if (sortType == LIKE_COUNT) {
            return restaurant.likeCount.desc();
        }
        return distanceColumn.asc();
    }

    private LongSupplier totalCountSupplier(RestaurantSearchCond restaurantCond, LocationSearchCond locationCond) {
        JPAQuery<Long> countQuery = query.select(restaurant.countDistinct())
                .from(restaurant)
                .join(video).on(video.restaurant.eq(restaurant))
                .join(celeb).on(celeb.eq(video.celeb))
                .where(
                        celebIdEqual(restaurantCond.celebId),
                        restaurantCategoryEqual(restaurantCond.category),
                        restaurantNameLike(restaurantCond.restaurantName),
                        restaurantInArea(locationCond)
                );
        return countQuery::fetchOne;
    }

    public Page<RestaurantSearchResponse> findNearBy(
            long restaurantId,
            int distance,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        Restaurant standard = restaurantQueryDaoSupport.getById(restaurantId);
        List<RestaurantSearchResponse> result = findRestaurants(restaurantId, distance, pageable, standard);
        LongSupplier totalCountSupplier = totalCountSupplier(restaurantId, distance, standard);
        Page<RestaurantSearchResponse> restaurants = PageableExecutionUtils.getPage(result, pageable,
                totalCountSupplier);
        settingCelebAndImageAndLiked(memberId, restaurants);
        return restaurants;
    }

    private List<RestaurantSearchResponse> findRestaurants(
            long restaurantId,
            int distance,
            Pageable pageable,
            Restaurant standard
    ) {
        return query.select(Projections.constructor(RestaurantSearchResponse.class,
                        restaurant.id,
                        restaurant.name,
                        restaurant.category,
                        restaurant.roadAddress,
                        restaurant.latitude,
                        restaurant.longitude,
                        restaurant.phoneNumber,
                        restaurant.naverMapUrl,
                        restaurant.viewCount,
                        distance(standard.latitude(), standard.longitude()).as(distanceColumn),
                        restaurant.likeCount
                ))
                .from(restaurant)
                .where(
                        distanceMinOrEqual(standard, distance),
                        restaurantIdNotEqual(restaurantId)
                ).orderBy(distanceColumn.asc(), restaurant.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression restaurantIdNotEqual(Long id) {
        return restaurant.id.ne(id);
    }

    private BooleanExpression distanceMinOrEqual(Restaurant standard, int distance) {
        return distance(standard.latitude(), standard.longitude()).loe(distance);
    }

    private LongSupplier totalCountSupplier(long restaurantId, int distance, Restaurant standard) {
        JPAQuery<Long> countQuery = query.select(restaurant.count())
                .from(restaurant)
                .where(
                        distanceMinOrEqual(standard, distance),
                        restaurantIdNotEqual(restaurantId)
                );
        return countQuery::fetchOne;
    }

    private void settingCelebAndImageAndLiked(
            @Nullable Long memberId, Page<RestaurantSearchResponse> restaurants
    ) {
        List<Long> restaurantIds = restaurants.map(RestaurantSearchResponse::id).toList();
        Map<Long, List<Celeb>> celebsMap = getCelebsGroupByRestaurantsId(restaurantIds);
        Map<Long, List<RestaurantImage>> restaurantImageMap = getImagesGroupByRestaurantsId(restaurantIds);
        Map<Long, Boolean> isLikedMap = getIsLikedGroupByRestaurantsId(memberId, restaurantIds);
        for (RestaurantSearchResponse response : restaurants) {
            response.setCelebs(celebsMap.get(response.id()).stream().map(CelebQueryResponse::of).toList());
            if (restaurantImageMap.get(response.id()) != null) {
                response.setImages(
                        restaurantImageMap.get(response.id()).stream().map(RestaurantImageQueryResponse::of).toList()
                );
            }
            response.setLiked(isLikedMap.get(response.id()));
        }
    }

    private Map<Long, Boolean> getIsLikedGroupByRestaurantsId(@Nullable Long memberId, List<Long> restaurantIds) {
        Set<Long> likedRestaurantIds = restaurantLikeQueryDaoSupport
                .findAllByMemberIdAndRestaurantIdIn(memberId, restaurantIds)
                .stream()
                .map(RestaurantLike::restaurant)
                .map(BaseEntity::id)
                .collect(toSet());
        return restaurantIds.stream()
                .collect(toMap(identity(), likedRestaurantIds::contains));
    }

    private Map<Long, List<Celeb>> getCelebsGroupByRestaurantsId(List<Long> restaurantIds) {
        List<Video> videos = videoQueryDaoSupport.findAllByRestaurantIdIn(restaurantIds);
        Map<Long, List<Video>> restaurantVideos = groupBySameOrder(videos, video -> video.restaurant().id());
        return restaurantVideos.entrySet()
                .stream()
                .map(it -> Map.entry(it.getKey(), videosToCelebs(it)))
                .collect(toMap(
                        Entry::getKey,
                        Entry::getValue
                ));
    }

    private Map<Long, List<RestaurantImage>> getImagesGroupByRestaurantsId(List<Long> restaurantIds) {
        List<RestaurantImage> images = restaurantImageQueryDaoSupport.findAllByRestaurantIdIn(restaurantIds);
        return groupBySameOrder(images, image -> image.restaurant().id());
    }

    private List<Celeb> videosToCelebs(Entry<Long, List<Video>> idWithVideosEntry) {
        return idWithVideosEntry.getValue()
                .stream()
                .map(Video::celeb)
                .toList();
    }

    @RequiredArgsConstructor
    public enum RestaurantSortType {

        DISTANCE("distance"),
        LIKE_COUNT("like");
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

        public double middleLat() {
            return calculateMiddle(lowLatitude, highLatitude);
        }

        public double middleLng() {
            return calculateMiddle(lowLongitude, highLongitude);
        }
    }

    private static double calculateMiddle(double x, double y) {
        return (x + y) / 2.0;
    }
}
