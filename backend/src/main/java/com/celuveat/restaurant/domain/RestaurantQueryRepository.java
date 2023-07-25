package com.celuveat.restaurant.domain;

import static com.celuveat.common.util.DynamicQueryUtil.appendQueryIfTrue;
import static com.celuveat.common.util.DynamicQueryUtil.notNull;
import static com.celuveat.common.util.DynamicQueryUtil.notNullRecursive;
import static com.celuveat.common.util.StringUtil.removeAllBlank;
import static org.springframework.util.StringUtils.hasText;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
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

    private static final String WHERE = "WHERE ";
    private static final String AND = " AND ";

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
        List<String> appendedQuery = new ArrayList<>();
        appendQueryIfTrue(appendedQuery,
                notNull(restaurantSearchCond.celebId()),
                CELEB_ID_EQUAL,
                restaurantSearchCond.celebId());
        appendQueryIfTrue(appendedQuery,
                hasText(restaurantSearchCond.category()),
                RESTAURANT_CATEGORY_EQUAL,
                restaurantSearchCond.category());
        appendQueryIfTrue(appendedQuery,
                hasText(restaurantSearchCond.restaurantName()),
                RESTAURANT_NAME_LIKE_IGNORE_CASE_IGNORE_BLANK,
                removeAllBlank(restaurantSearchCond.restaurantName()));
        appendQueryIfTrue(appendedQuery,
                notNullRecursive(locationSearchCond),
                RESTAURANT_IN_AREA,
                locationSearchCond.lowLatitude, locationSearchCond.highLatitude,
                locationSearchCond.lowLongitude, locationSearchCond.highLongitude);
        String query = createQuery(appendedQuery);
        List<Restaurant> resultList = em.createQuery(query, Restaurant.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        String countQuery = createCountQuery(appendedQuery);
        return PageableExecutionUtils.getPage(
                resultList,
                pageable,
                () -> (Long) em.createQuery(countQuery).getSingleResult()
        );
    }

    private String createQuery(List<String> appendedQuery) {
        if (appendedQuery.isEmpty()) {
            return SELECT_RESTAURANT_JOIN_VIDEO_AND_CELEB;
        }
        return SELECT_RESTAURANT_JOIN_VIDEO_AND_CELEB
               + WHERE
               + String.join(AND, appendedQuery);
    }

    private String createCountQuery(List<String> appendedQuery) {
        if (appendedQuery.isEmpty()) {
            return COUNT_QUERY;
        }
        return COUNT_QUERY
               + WHERE
               + String.join(AND, appendedQuery);
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

