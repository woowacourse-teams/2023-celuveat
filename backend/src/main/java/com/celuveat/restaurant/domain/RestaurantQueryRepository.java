package com.celuveat.restaurant.domain;

import static com.celuveat.common.util.DynamicJpqlUtil.appendQueryIfTrue;
import static com.celuveat.common.util.DynamicJpqlUtil.notNull;
import static com.celuveat.common.util.StringUtil.replaceAllBlank;
import static java.util.stream.Collectors.groupingBy;
import static org.springframework.util.StringUtils.hasText;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.video.domain.Video;
import com.celuveat.video.domain.VideoRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantQueryRepository {

    private static final String WHERE = "WHERE ";
    private static final String AND = "AND ";

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
    private final RestaurantImageRepository restaurantImageRepository;
    private final VideoRepository videoRepository;
    private final EntityManager em;

    public List<RestaurantQueryResponse> findAll(RestaurantSearchCond cond) {
        List<Restaurant> restaurants = getRestaurants(cond);
        List<Video> videos = findVideoByRestaurantIdsIn(restaurants);
        Map<Restaurant, List<Celeb>> celebs = mapToCeleb(groupingVideoByRestaurant(videos));
        List<RestaurantImage> images = findImageByRestaurantIdsIn(restaurants);
        Map<Restaurant, List<RestaurantImage>> restaurantListMap = groupingImageByRestaurant(images);
        return toResponseList(celebs, restaurantListMap);
    }

    private List<Restaurant> getRestaurants(RestaurantSearchCond cond) {
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

    private List<Video> findVideoByRestaurantIdsIn(List<Restaurant> restaurantIds) {
        return videoRepository.findAllByRestaurantIn(restaurantIds);
    }

    private Map<Restaurant, List<Video>> groupingVideoByRestaurant(List<Video> videos) {
        return videos.stream()
                .collect(groupingBy(Video::restaurant, LinkedHashMap::new, Collectors.toList()));
    }

    private Map<Restaurant, List<Celeb>> mapToCeleb(Map<Restaurant, List<Video>> restaurantVideos) {
        Map<Restaurant, List<Celeb>> celebs = new LinkedHashMap<>();
        for (Restaurant restaurant : restaurantVideos.keySet()) {
            List<Celeb> list = restaurantVideos.get(restaurant).stream()
                    .map(Video::celeb)
                    .toList();
            celebs.put(restaurant, list);
        }
        return celebs;
    }

    private List<RestaurantImage> findImageByRestaurantIdsIn(List<Restaurant> restaurantIds) {
        return restaurantImageRepository.findAllByRestaurantIn(restaurantIds);
    }

    private Map<Restaurant, List<RestaurantImage>> groupingImageByRestaurant(List<RestaurantImage> images) {
        return images.stream()
                .collect(groupingBy(RestaurantImage::restaurant, LinkedHashMap::new, Collectors.toList()));
    }

    private List<RestaurantQueryResponse> toResponseList(
            Map<Restaurant, List<Celeb>> celebs,
            Map<Restaurant, List<RestaurantImage>> images
    ) {
        return images.keySet().stream()
                .map(restaurant -> toResponse(celebs.get(restaurant), images.get(restaurant), restaurant))
                .toList();
    }

    private RestaurantQueryResponse toResponse(
            List<Celeb> celebs,
            List<RestaurantImage> images,
            Restaurant restaurant
    ) {
        return RestaurantQueryResponse.from(restaurant, celebs, images);
    }

    record RestaurantSearchCond(
            Long celebId,
            String category,
            String restaurantName
    ) {
    }
}

