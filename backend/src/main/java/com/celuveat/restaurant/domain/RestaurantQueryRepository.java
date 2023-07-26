package com.celuveat.restaurant.domain;

import static com.celuveat.common.query.DynamicQueryCondition.notNull;
import static com.celuveat.common.query.DynamicQueryCondition.notNullRecursive;
import static com.celuveat.common.util.StringUtil.removeAllBlank;
import static org.springframework.util.StringUtils.hasText;

import com.celuveat.common.query.DynamicQuery;
import com.celuveat.common.query.DynamicQueryAssembler;
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

    private static final String SELECT_RESTAURANT_JOIN_VIDEO_AND_CELEB = """
            SELECT DISTINCT r
            FROM Restaurant r
            JOIN Video v
            ON v.restaurant = r
            JOIN Celeb c
            ON c = v.celeb
            """;

    private static final String COUNT_QUERY = """
            SELECT DISTINCT count(*)
            FROM Restaurant r
            JOIN Video v
            ON v.restaurant = r
            JOIN Celeb c
            ON c = v.celeb
            """;

    private final EntityManager em;

    public Page<Restaurant> getRestaurants(
            RestaurantSearchCond restaurantSearchCond,
            LocationSearchCond locationSearchCond,
            Pageable pageable
    ) {
        DynamicQueryAssembler dynamicQueryAssembler = new DynamicQueryAssembler(
                celebIdEqual(restaurantSearchCond),
                restaurantCategoryEqual(restaurantSearchCond),
                restaurantNameLike(restaurantSearchCond),
                restaurantInArea(locationSearchCond)
        );
        String whereQuery = dynamicQueryAssembler.assemble();
        List<Restaurant> resultList = em
                .createQuery(SELECT_RESTAURANT_JOIN_VIDEO_AND_CELEB + whereQuery, Restaurant.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return PageableExecutionUtils.getPage(
                resultList,
                pageable,
                () -> (Long) em.createQuery(COUNT_QUERY + whereQuery).getSingleResult()
        );
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
}

