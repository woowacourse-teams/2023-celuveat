package com.celuveat.restaurant.domain;

import static com.celuveat.common.query.DynamicQueryCondition.notNull;
import static com.celuveat.common.query.DynamicQueryCondition.notNullRecursive;
import static com.celuveat.common.util.StringUtil.removeAllBlank;
import static org.springframework.util.StringUtils.hasText;

import com.celuveat.common.query.DynamicQuery;
import com.celuveat.common.query.DynamicQueryAssembler;
import com.celuveat.restaurant.domain.dto.RestaurantWithDistance;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantQueryRepository {

    private static final String HAVERSINE_FORMULA = """
            (6371 * acos(cos(radians(%s)) * cos(radians(latitude))
                 * cos(radians(longitude) - radians(%s))
                 + sin(radians(%s)) * sin(radians(latitude)))) * 1000
             """;

    private static final String SELECT_RESTAURANT_JOIN_VIDEO_AND_CELEB = """
            SELECT DISTINCT new com.celuveat.restaurant.domain.dto.RestaurantWithDistance(
                r.id,
                r.name,
                r.category,
                r.roadAddress,
                r.latitude,
                r.longitude,
                r.phoneNumber,
                r.naverMapUrl,
                %s AS dist
            )
            FROM Restaurant r
            JOIN Video v
            ON v.restaurant = r
            JOIN Celeb c
            ON c = v.celeb
            """;

    private static final String CELEB_ID_EQUAL = "c.id = %d";

    private static final String RESTAURANT_CATEGORY_EQUAL = "r.category = '%s'";

    private static final String RESTAURANT_NAME_LIKE_IGNORE_CASE_IGNORE_BLANK = """
            Function('replace', lower(r.name), ' ', '') like lower('%%%s%%')
            """;

    private static final String RESTAURANT_IN_AREA = """
            latitude BETWEEN %s AND %s
            AND
            longitude BETWEEN %s AND %s
            """;

    private static final String ORDER_BY_DISTANCE_ASC = """
            ORDER BY dist ASC
            """;

    private static final String COUNT_QUERY = """
            SELECT count(DISTINCT r)
            FROM Restaurant r
            JOIN Video v
            ON v.restaurant = r
            JOIN Celeb c
            ON c = v.celeb
            """;

    private static final String SELECT_RESTAURANT_NEARBY_SPECIFIC_DISTANCE = """
            SELECT new com.celuveat.restaurant.domain.dto.RestaurantWithDistance(
                r.id,
                r.name,
                r.category,
                r.roadAddress,
                r.latitude,
                r.longitude,
                r.phoneNumber,
                r.naverMapUrl,
                %s AS dist
            )
            FROM Restaurant r
            """;

    private static final String WHERE_MIN_OR_EQUAL_DISTANCE_AND_NOT_SAME_RESTAURANT = """
            WHERE %s <= %d AND id != %d
            """;

    private static final String COUNT_QUERY_NEARBY_DISTANCE = """
            SELECT count(r)
            FROM Restaurant r
            """;

    private final EntityManager em;

    public Page<RestaurantWithDistance> getRestaurantsWithDistance(
            RestaurantSearchCond restaurantSearchCond,
            LocationSearchCond locationSearchCond,
            Pageable pageable
    ) {
        double middleLat = calculateMiddle(locationSearchCond.lowLatitude, locationSearchCond.highLatitude);
        double middleLng = calculateMiddle(locationSearchCond.lowLongitude, locationSearchCond.highLongitude);
        String whereQuery = DynamicQueryAssembler.assemble(
                celebIdEqual(restaurantSearchCond),
                restaurantCategoryEqual(restaurantSearchCond),
                restaurantNameLike(restaurantSearchCond),
                restaurantInArea(locationSearchCond)
        );
        List<RestaurantWithDistance> resultList = em.createQuery(
                        SELECT_RESTAURANT_JOIN_VIDEO_AND_CELEB.formatted(getDistanceColumn(middleLat, middleLng))
                                + whereQuery
                                + ORDER_BY_DISTANCE_ASC,
                        RestaurantWithDistance.class
                )
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return PageableExecutionUtils.getPage(
                resultList,
                pageable,
                () -> (Long) em.createQuery(COUNT_QUERY + whereQuery).getSingleResult()
        );
    }

    private String getDistanceColumn(double middleLat, double middleLng) {
        return HAVERSINE_FORMULA.formatted(middleLat, middleLng, middleLat);
    }

    private double calculateMiddle(double x, double y) {
        return (x + y) / 2.0;
    }

    private DynamicQuery celebIdEqual(RestaurantSearchCond restaurantSearchCond) {
        return DynamicQuery.builder()
                .query(CELEB_ID_EQUAL)
                .params(restaurantSearchCond.celebId())
                .condition(notNull(restaurantSearchCond.celebId()))
                .build();
    }

    private DynamicQuery restaurantCategoryEqual(RestaurantSearchCond restaurantSearchCond) {
        return DynamicQuery.builder()
                .query(RESTAURANT_CATEGORY_EQUAL)
                .params(restaurantSearchCond.category())
                .condition(hasText(restaurantSearchCond.category()))
                .build();
    }

    private DynamicQuery restaurantNameLike(RestaurantSearchCond restaurantSearchCond) {
        return DynamicQuery.builder()
                .query(RESTAURANT_NAME_LIKE_IGNORE_CASE_IGNORE_BLANK)
                .params(removeAllBlank(restaurantSearchCond.restaurantName()))
                .condition(hasText(restaurantSearchCond.restaurantName()))
                .build();
    }

    private DynamicQuery restaurantInArea(LocationSearchCond locationSearchCond) {
        return DynamicQuery.builder()
                .query(RESTAURANT_IN_AREA)
                .params(locationSearchCond.lowLatitude, locationSearchCond.highLatitude,
                        locationSearchCond.lowLongitude, locationSearchCond.highLongitude)
                .condition(notNullRecursive(locationSearchCond))
                .build();
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

    public Page<RestaurantWithDistance> getRestaurantsWithDistanceNearBy(
            int distance,
            Restaurant restaurant,
            Pageable pageable
    ) {
        String dist = getDistanceColumn(restaurant.latitude(), restaurant.longitude());
        String whereQuery = WHERE_MIN_OR_EQUAL_DISTANCE_AND_NOT_SAME_RESTAURANT.formatted(dist, distance, restaurant.id());
        List<RestaurantWithDistance> result = em.createQuery(
                        SELECT_RESTAURANT_NEARBY_SPECIFIC_DISTANCE.formatted(dist)
                                + whereQuery
                                + ORDER_BY_DISTANCE_ASC,
                        RestaurantWithDistance.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return PageableExecutionUtils.getPage(
                result,
                pageable,
                () -> (Long) em.createQuery(COUNT_QUERY_NEARBY_DISTANCE + whereQuery).getSingleResult()
        );
    }
}
