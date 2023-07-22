package com.celuveat.restaurant.domain;

import static com.celuveat.common.util.DynamicQueryUtil.appendQueryIfTrue;
import static com.celuveat.common.util.DynamicQueryUtil.notNull;
import static com.celuveat.common.util.StringUtil.replaceAllBlank;
import static org.springframework.util.StringUtils.hasText;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantQueryRepository {

    private static final String WHERE = "WHERE ";
    private static final String AND = " AND ";

    private static final String CELEB_ID_EQ = "c.id = %d";
    private static final String RESTAURANT_CATEGORY_EQ = "r.category = '%s'";
    private static final String RESTAURANT_NAME_LIKE_IGNORE_CASE_IGNORE_BLANK = """
            Function('replace', lower(r.name), ' ', '') like lower('%%%s%%')
            """;
    private static final String SELECT_RESTAURANT_JOIN_VIDEO_AND_CELEB = """
            SELECT r
            FROM Restaurant r
            JOIN Video v
            ON v.restaurant = r
            JOIN Celeb c
            ON c = v.celeb
            """;
    private final EntityManager em;

    public List<Restaurant> getRestaurants(RestaurantSearchCond cond) {
        List<String> appendedQuery = new ArrayList<>();
        appendQueryIfTrue(appendedQuery,
                notNull(cond.celebId()),
                CELEB_ID_EQ,
                cond.celebId());
        appendQueryIfTrue(appendedQuery,
                hasText(cond.category()),
                RESTAURANT_CATEGORY_EQ,
                cond.category());
        appendQueryIfTrue(appendedQuery,
                hasText(cond.restaurantName()),
                RESTAURANT_NAME_LIKE_IGNORE_CASE_IGNORE_BLANK,
                replaceAllBlank(cond.restaurantName()));
        String query = createQuery(appendedQuery);
        return em.createQuery(query, Restaurant.class).getResultList();
    }

    private String createQuery(List<String> appendedQuery) {
        StringBuilder query = new StringBuilder(SELECT_RESTAURANT_JOIN_VIDEO_AND_CELEB);
        if (!appendedQuery.isEmpty()) {
            query.append(WHERE);
            for (String appended : appendedQuery) {
                query.append(appended).append(AND);
            }
            query.replace(query.length() - AND.length(), query.length(), "");
        }
        return query.toString();
    }

    public record RestaurantSearchCond(
            Long celebId,
            String category,
            String restaurantName
    ) {
    }
}

