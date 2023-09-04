package com.celuveat.restaurant.query;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.query.RestaurantEntityManagerQueryRepositoryImpl.LocationSearchCond;
import com.celuveat.restaurant.query.RestaurantEntityManagerQueryRepositoryImpl.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.query.dto.RestaurantIdWithLikeCount;
import com.celuveat.restaurant.query.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSimpleResponse;
import com.celuveat.restaurant.query.dto.RestaurantWithDistance;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.query.dao.VideoQueryDaoSupport;
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

    private final RestaurantLikeQueryRepository restaurantLikeQueryRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;
    private final VideoQueryDaoSupport videoQueryDaoSupport;
    private final RestaurantImageQueryRepository restaurantImageQueryRepository;

    public RestaurantDetailResponse findRestaurantDetailById(Long restaurantId, Optional<Long> memberId) {
        Restaurant restaurant = restaurantQueryRepository.getById(restaurantId);
        return mapToRestaurantWithCelebAndImagesDetailResponse(restaurant, memberId);
    }

    private RestaurantDetailResponse mapToRestaurantWithCelebAndImagesDetailResponse(
            Restaurant restaurant,
            Optional<Long> memberId
    ) {
        List<Celeb> celebs = getCelebsByRestaurant(restaurant);
        List<RestaurantImage> restaurantImages = restaurantImageQueryRepository.findAllByRestaurant(restaurant);
        int likeCount = restaurantLikeQueryRepository.countByRestaurant(restaurant);
        return RestaurantDetailResponse.builder()
                .restaurant(restaurant)
                .celebs(celebs)
                .restaurantImages(restaurantImages)
                .likeCount(likeCount)
                .isLiked(applyLikedRestaurant(restaurant, memberId))
                .build();
    }

    private List<Celeb> getCelebsByRestaurant(Restaurant restaurant) {
        return videoQueryDaoSupport.findAllByRestaurant(restaurant).stream()
                .map(Video::celeb)
                .toList();
    }

    private boolean applyLikedRestaurant(Restaurant restaurant, Optional<Long> memberId) {
        return memberId.isPresent()
                && restaurantLikeQueryRepository.findByRestaurantAndMemberId(restaurant, memberId.get()).isPresent();
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

    private List<Long> extractRestaurantIds(Page<RestaurantWithDistance> restaurantsWithDistance) {
        return restaurantsWithDistance.getContent().stream()
                .map(RestaurantWithDistance::id)
                .toList();
    }

    private RestaurantsIdWithCelebsAndImagesGroupByRestaurantId restaurantDatasGroupByRestaurantId(
            List<Long> restaurantIds
    ) {
        List<Video> videos = videoQueryDaoSupport.findAllByRestaurantIdIn(restaurantIds);
        List<RestaurantImage> images = restaurantImageQueryRepository.findAllByRestaurantIdIn(restaurantIds);
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

    private Set<Long> getRestaurantIds(Long memberId) {
        return restaurantLikeQueryRepository.findAllByMemberId(memberId).stream()
                .map(RestaurantLike::restaurant)
                .map(Restaurant::id)
                .collect(toUnmodifiableSet());
    }

    private Map<Long, Long> likeCountGroupByRestaurantIds(List<Long> restaurantIds) {
        return restaurantLikeQueryRepository.likeCountGroupByRestaurantsId(restaurantIds)
                .stream()
                .collect(toMap(
                        RestaurantIdWithLikeCount::restaurantId,
                        RestaurantIdWithLikeCount::count
                ));
    }

    public Page<RestaurantSimpleResponse> findAllNearByDistanceWithoutSpecificRestaurant(
            int distance,
            long restaurantId,
            Optional<Long> memberId,
            Pageable pageable
    ) {
        Page<RestaurantWithDistance> restaurantsWithDistance
                = restaurantQueryRepository.getRestaurantsNearByRestaurantId(distance, restaurantId, pageable);
        return mapToRestaurantWithCelebAndImagesSimpleResponse(restaurantsWithDistance, memberId.orElse(null));
    }

    public List<RestaurantLikeQueryResponse> findAllLikedRestaurantByMemberId(Long memberId) {
        List<RestaurantLike> restaurantLikes =
                restaurantLikeQueryRepository.findAllByMemberIdOrderByCreatedDateDesc(memberId);
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
