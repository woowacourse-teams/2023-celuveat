package com.celuveat.restaurant.query.dao;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.query.dto.RestaurantLikeQueryResponse;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.query.dao.VideoQueryDaoSupport;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantLikeQueryResponseDao {

    private final VideoQueryDaoSupport videoQueryDaoSupport;
    private final RestaurantLikeQueryDaoSupport restaurantLikeQueryDaoSupport;
    private final RestaurantImageQueryDaoSupport restaurantImageQueryDaoSupport;

    public List<RestaurantLikeQueryResponse> findAllLikedRestaurantByMemberId(Long memberId) {
        List<RestaurantLike> restaurantLikes =
                restaurantLikeQueryDaoSupport.findAllByMemberIdOrderByCreatedDateDesc(memberId);
        return mapToRestaurantLikeQueryResponse(restaurantLikes);
    }

    private List<RestaurantLikeQueryResponse> mapToRestaurantLikeQueryResponse(List<RestaurantLike> restaurantLikes) {
        List<Restaurant> restaurants = extractRestaurant(restaurantLikes);
        List<Long> restaurantIds = extractRestaurantIds(restaurants);
        RestaurantsIdWithCelebsAndImagesGroupByRestaurantId restaurantDatasGroup =
                restaurantDatasGroupByRestaurantId(restaurantIds);
        return restaurants.stream()
                .map(restaurant -> RestaurantLikeQueryResponse.builder()
                        .restaurant(restaurant)
                        .celebs(restaurantDatasGroup.get(restaurant.id()).celebs())
                        .restaurantImages(restaurantDatasGroup.get(restaurant.id()).restaurantImages())
                        .build()
                ).toList();
    }

    private List<Restaurant> extractRestaurant(List<RestaurantLike> restaurantLikes) {
        return restaurantLikes.stream()
                .map(RestaurantLike::restaurant)
                .toList();
    }

    private List<Long> extractRestaurantIds(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(Restaurant::id)
                .toList();
    }

    private RestaurantsIdWithCelebsAndImagesGroupByRestaurantId restaurantDatasGroupByRestaurantId(
            List<Long> restaurantIds
    ) {
        List<Video> videos = videoQueryDaoSupport.findAllByRestaurantIdIn(restaurantIds);
        List<RestaurantImage> images = restaurantImageQueryDaoSupport.findAllByRestaurantIdIn(restaurantIds);
        Map<Long, List<Celeb>> celebsMap = toCelebsGroupByRestaurantId(videos);
        Map<Long, List<RestaurantImage>> restaurantMap = groupingImageByRestaurant(images);
        return new RestaurantsIdWithCelebsAndImagesGroupByRestaurantId(
                restaurantIds.stream()
                        .collect(toMap(identity(),
                                it -> new RestaurantsIdWithCelebsAndImages(
                                        it, celebsMap.get(it), restaurantMap.get(it))
                        ))
        );
    }

    private Map<Long, List<Celeb>> toCelebsGroupByRestaurantId(List<Video> videos) {
        Map<Long, List<Video>> restaurantVideos = videos.stream()
                .collect(groupingBy(
                        video -> video.restaurant().id(),
                        LinkedHashMap::new,
                        toList()
                ));
        return mapVideoToCeleb(restaurantVideos);
    }

    private Map<Long, List<Celeb>> mapVideoToCeleb(Map<Long, List<Video>> restaurantVideos) {
        Map<Long, List<Celeb>> celebs = new LinkedHashMap<>();
        for (Long restaurantId : restaurantVideos.keySet()) {
            List<Celeb> list = restaurantVideos.get(restaurantId).stream()
                    .map(Video::celeb)
                    .toList();
            celebs.put(restaurantId, list);
        }
        return celebs;
    }

    private Map<Long, List<RestaurantImage>> groupingImageByRestaurant(List<RestaurantImage> images) {
        return images.stream()
                .collect(groupingBy(
                        image -> image.restaurant().id(),
                        LinkedHashMap::new,
                        toList()
                ));
    }

    private record RestaurantsIdWithCelebsAndImagesGroupByRestaurantId(
            Map<Long, RestaurantsIdWithCelebsAndImages> map
    ) {
        public RestaurantsIdWithCelebsAndImages get(Long id) {
            return map.get(id);
        }
    }

    private record RestaurantsIdWithCelebsAndImages(
            Long restaurantId,
            List<Celeb> celebs,
            List<RestaurantImage> restaurantImages
    ) {
    }
}
