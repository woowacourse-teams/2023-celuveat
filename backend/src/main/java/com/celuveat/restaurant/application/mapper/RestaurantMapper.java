package com.celuveat.restaurant.application.mapper;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.application.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantWithCelebAndImagesDetailResponse;
import com.celuveat.restaurant.application.dto.RestaurantWithCelebAndImagesSimpleResponse;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.restaurant.domain.RestaurantLike;
import com.celuveat.restaurant.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.domain.dto.RestaurantIdWithLikeCount;
import com.celuveat.restaurant.domain.dto.RestaurantWithDistance;
import com.celuveat.video.domain.Video;
import com.celuveat.video.domain.VideoRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantMapper {

    private final VideoRepository videoRepository;
    private final RestaurantLikeRepository restaurantLikeRepository;
    private final RestaurantImageRepository restaurantImageRepository;

    public RestaurantWithCelebAndImagesDetailResponse mapToRestaurantWithCelebAndImagesDetailResponse(
            Restaurant restaurant
    ) {
        List<Celeb> celebs = getCelebsByRestaurant(restaurant);
        List<RestaurantImage> restaurantImages = restaurantImageRepository.findAllByRestaurant(restaurant);
        int likeCount = restaurantLikeRepository.countByRestaurant(restaurant);
        return RestaurantWithCelebAndImagesDetailResponse.builder()
                .restaurant(restaurant)
                .celebs(celebs)
                .restaurantImages(restaurantImages)
                .likeCount(likeCount)
                .build();
    }

    private List<Celeb> getCelebsByRestaurant(Restaurant restaurant) {
        return videoRepository.findAllByRestaurant(restaurant).stream()
                .map(Video::celeb)
                .toList();
    }

    public Page<RestaurantWithCelebAndImagesSimpleResponse> mapToRestaurantWithCelebAndImagesSimpleResponse(
            Page<RestaurantWithDistance> restaurants, Long memberId
    ) {
        List<Long> restaurantIds = extractRestaurantIds(restaurants);
        RestaurantsIdWithCelebsAndImagesGroupByRestaurantId restaurantDatasGroup =
                restaurantDatasGroupByRestaurantId(restaurantIds);
        Set<Long> likedRestaurantIds = restaurantLikeRepository.findAllByMemberId(memberId).stream()
                .map(RestaurantLike::restaurant)
                .map(Restaurant::id)
                .collect(toUnmodifiableSet());
        Map<Long, Long> likeCountsGroupById = restaurantLikeRepository.likeCountGroupByRestaurantsId(restaurantIds)
                .stream()
                .collect(toMap(
                        RestaurantIdWithLikeCount::restaurantId,
                        RestaurantIdWithLikeCount::count
                ));
        return restaurants.map(restaurant ->
                RestaurantWithCelebAndImagesSimpleResponse.builder()
                        .restaurant(restaurant)
                        .celebs(restaurantDatasGroup.get(restaurant.id()).celebs())
                        .restaurantImages(restaurantDatasGroup.get(restaurant.id()).restaurantImages())
                        .isLiked(likedRestaurantIds.contains(restaurant.id()))
                        .likeCount(likeCountsGroupById.get(restaurant.id()))
                        .build()
        );
    }

    private List<Long> extractRestaurantIds(Page<RestaurantWithDistance> restaurantsWithDistance) {
        return restaurantsWithDistance.getContent().stream()
                .map(RestaurantWithDistance::id)
                .toList();
    }

    private RestaurantsIdWithCelebsAndImagesGroupByRestaurantId restaurantDatasGroupByRestaurantId(
            List<Long> restaurantIds
    ) {
        List<Video> videos = videoRepository.findAllByRestaurantIdIn(restaurantIds);
        List<RestaurantImage> images = restaurantImageRepository.findAllByRestaurantIdIn(restaurantIds);
        Map<Long, List<Celeb>> celebsMap = toCelebsGroupByRestaurantId(videos);
        Map<Long, List<RestaurantImage>> restaurantMap = groupingImageByRestaurant(images);
        return new RestaurantsIdWithCelebsAndImagesGroupByRestaurantId(restaurantIds.stream()
                .collect(toMap(Function.identity(),
                        it -> new RestaurantsIdWithCelebsAndImages(it, celebsMap.get(it), restaurantMap.get(it)))
                ));
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

    public List<RestaurantLikeQueryResponse> mapToRestaurantLikeQueryResponse(List<RestaurantLike> restaurantLikes) {
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
