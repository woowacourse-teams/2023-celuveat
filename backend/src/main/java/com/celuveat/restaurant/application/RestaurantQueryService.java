package com.celuveat.restaurant.application;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.application.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.application.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantSimpleResponse;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.restaurant.domain.RestaurantLike;
import com.celuveat.restaurant.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.domain.RestaurantQueryRepository;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.LocationSearchCond;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.restaurant.domain.dto.RestaurantIdWithLikeCount;
import com.celuveat.restaurant.domain.dto.RestaurantWithDistance;
import com.celuveat.video.domain.Video;
import com.celuveat.video.domain.VideoRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantQueryService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantLikeRepository restaurantLikeRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;
    private final VideoRepository videoRepository;
    private final RestaurantImageRepository restaurantImageRepository;

    public RestaurantDetailResponse findRestaurantDetailById(Long restaurantId, Optional<Long> memberId) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        return mapToRestaurantWithCelebAndImagesDetailResponse(restaurant, memberId);
    }

    private RestaurantDetailResponse mapToRestaurantWithCelebAndImagesDetailResponse(
            Restaurant restaurant,
            Optional<Long> memberId
    ) {
        List<Celeb> celebs = getCelebsByRestaurant(restaurant);
        List<RestaurantImage> restaurantImages = restaurantImageRepository.findAllByRestaurant(restaurant);
        int likeCount = restaurantLikeRepository.countByRestaurant(restaurant);
        return RestaurantDetailResponse.builder()
                .restaurant(restaurant)
                .celebs(celebs)
                .restaurantImages(restaurantImages)
                .likeCount(likeCount)
                .isLiked(applyLikedRestaurant(restaurant, memberId))
                .build();
    }

    private List<Celeb> getCelebsByRestaurant(Restaurant restaurant) {
        return videoRepository.findAllByRestaurant(restaurant).stream()
                .map(Video::celeb)
                .toList();
    }

    private boolean applyLikedRestaurant(Restaurant restaurant, Optional<Long> memberId) {
        return memberId.isPresent()
                && restaurantLikeRepository.findByRestaurantAndMemberId(restaurant, memberId.get()).isPresent();
    }

    public Page<RestaurantSimpleResponse> findAllWithMemberLiked(
            RestaurantSearchCond restaurantCond,
            LocationSearchCond locationCond,
            Pageable pageable,
            Long memberId
    ) {
        Page<RestaurantWithDistance> restaurantsWithDistance = restaurantQueryRepository.getRestaurantsWithDistance(
                restaurantCond, locationCond, pageable
        );
        return mapToRestaurantWithCelebAndImagesSimpleResponse(restaurantsWithDistance, memberId);
    }

    private Page<RestaurantSimpleResponse> mapToRestaurantWithCelebAndImagesSimpleResponse(
            Page<RestaurantWithDistance> restaurants, Long memberId
    ) {
        List<Long> restaurantIds = extractRestaurantIds(restaurants);
        RestaurantsIdWithCelebsAndImagesGroupByRestaurantId restaurantDatasGroup =
                restaurantDatasGroupByRestaurantId(restaurantIds);
        Set<Long> likedRestaurantIds = getRestaurantIds(memberId);
        Map<Long, Long> likeCountsGroupById = likeCountGroupByRestaurantIds(restaurantIds);
        return restaurants.map(restaurant ->
                RestaurantSimpleResponse.builder()
                        .restaurant(restaurant)
                        .celebs(restaurantDatasGroup.get(restaurant.id()).celebs())
                        .restaurantImages(restaurantDatasGroup.get(restaurant.id()).restaurantImages())
                        .isLiked(likedRestaurantIds.contains(restaurant.id()))
                        .likeCount(likeCountsGroupById.get(restaurant.id()))
                        .build()
        );
    }

    private Set<Long> getRestaurantIds(Long memberId) {
        return restaurantLikeRepository.findAllByMemberId(memberId).stream()
                .map(RestaurantLike::restaurant)
                .map(Restaurant::id)
                .collect(toUnmodifiableSet());
    }

    private Map<Long, Long> likeCountGroupByRestaurantIds(List<Long> restaurantIds) {
        return restaurantLikeRepository.likeCountGroupByRestaurantsId(restaurantIds)
                .stream()
                .collect(toMap(
                        RestaurantIdWithLikeCount::restaurantId,
                        RestaurantIdWithLikeCount::count
                ));
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

    public Page<RestaurantSimpleResponse> findAllNearByDistanceWithoutSpecificRestaurant(
            int distance,
            long restaurantId,
            Pageable pageable
    ) {
        Page<RestaurantWithDistance> restaurantsWithDistance = restaurantQueryRepository.getRestaurantsNearByRestaurantId(
                distance, restaurantId, pageable
        );
        return mapToRestaurantWithCelebAndImagesSimpleResponse(restaurantsWithDistance, null);
    }

    public List<RestaurantLikeQueryResponse> findAllLikedRestaurantByMemberId(Long memberId) {
        List<RestaurantLike> restaurantLikes =
                restaurantLikeRepository.findAllByMemberIdOrderByCreatedDateDesc(memberId);
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
