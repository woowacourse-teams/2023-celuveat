package com.celuveat.restaurant.application;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.application.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.restaurant.domain.RestaurantLike;
import com.celuveat.restaurant.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.domain.RestaurantQueryRepository;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.LocationSearchCond;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.restaurant.domain.dto.RestaurantIdWithCount;
import com.celuveat.restaurant.domain.dto.RestaurantWithDistance;
import com.celuveat.video.domain.Video;
import com.celuveat.video.domain.VideoRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantQueryService {

    private final VideoRepository videoRepository;
    private final RestaurantRepository restaurantRepository;
    private final OauthMemberRepository oauthMemberRepository;
    private final RestaurantLikeRepository restaurantLikeRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;

    public Page<RestaurantQueryResponse> findAll(
            RestaurantSearchCond restaurantSearchCond,
            LocationSearchCond locationSearchCond,
            Pageable pageable
    ) {
        Page<RestaurantWithDistance> restaurantsWithDistance = restaurantQueryRepository.getRestaurantsWithDistance(
                restaurantSearchCond, locationSearchCond, pageable
        );
        return toRestaurantQueryResponsesPage(pageable, restaurantsWithDistance);
    }

    private Page<RestaurantQueryResponse> toRestaurantQueryResponsesPage(
            Pageable pageable,
            Page<RestaurantWithDistance> restaurantsWithDistance
    ) {
        List<Long> restaurantIds = extractRestaurantIds(restaurantsWithDistance);
        List<Video> videos = videoRepository.findAllByRestaurantIdIn(restaurantIds);
        Map<Long, List<Celeb>> celebs = mapVideoToCeleb(groupingVideoByRestaurant(videos));
        List<RestaurantImage> images = restaurantImageRepository.findAllByRestaurantIdIn(restaurantIds);
        Map<Long, List<RestaurantImage>> restaurantListMap = groupingImageByRestaurant(images);
        List<RestaurantIdWithCount> restaurantIdsWithCount =
                restaurantLikeRepository.countLikesByRestaurantIds(restaurantIds);
        Map<Long, Integer> likeCounts = groupingLikeCountByRestaurant(restaurantIdsWithCount, restaurantIds);
        List<RestaurantQueryResponse> responseList =
                toResponseList(restaurantsWithDistance, celebs, restaurantListMap, likeCounts);
        return new PageImpl<>(responseList, pageable, restaurantsWithDistance.getTotalElements());
    }

    private List<Long> extractRestaurantIds(Page<RestaurantWithDistance> restaurantsWithDistance) {
        return restaurantsWithDistance.getContent().stream()
                .map(RestaurantWithDistance::id)
                .toList();
    }

    private Map<Long, List<Video>> groupingVideoByRestaurant(List<Video> videos) {
        return videos.stream()
                .collect(groupingBy(
                        video -> video.restaurant().id(),
                        LinkedHashMap::new,
                        toList()
                ));
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

    private Map<Long, Integer> groupingLikeCountByRestaurant(
            List<RestaurantIdWithCount> restaurantIdsWithCount,
            List<Long> restaurantIds
    ) {
        Map<Long, Integer> likeCountByRestaurantId = restaurantIds.stream()
                .collect(toMap(identity(), likeCount -> 0));
        restaurantIdsWithCount.forEach(it -> likeCountByRestaurantId.put(it.restaurantId(), it.count().intValue()));
        return likeCountByRestaurantId;
    }

    private List<RestaurantQueryResponse> toResponseList(
            Page<RestaurantWithDistance> restaurants,
            Map<Long, List<Celeb>> celebs,
            Map<Long, List<RestaurantImage>> images,
            Map<Long, Integer> likeCounts
    ) {
        return restaurants.getContent().stream()
                .map(restaurant -> toResponse(
                        celebs.get(restaurant.id()),
                        images.get(restaurant.id()),
                        restaurant,
                        likeCounts.get(restaurant.id())
                )).toList();
    }

    private RestaurantQueryResponse toResponse(
            List<Celeb> celebs,
            List<RestaurantImage> images,
            RestaurantWithDistance restaurantWithDistance,
            Integer likeCount
    ) {
        return RestaurantQueryResponse.from(restaurantWithDistance, celebs, images, false, likeCount);
    }

    public Page<RestaurantQueryResponse> findAllWithMemberId(
            RestaurantSearchCond restaurantSearchCond,
            LocationSearchCond locationSearchCond,
            Pageable pageable,
            Long memberId
    ) {
        OauthMember member = oauthMemberRepository.getById(memberId);
        Set<Long> likedRestaurantIds = restaurantLikeRepository.findAllByMember(member).stream()
                .map(RestaurantLike::restaurant)
                .map(Restaurant::id)
                .collect(toUnmodifiableSet());

        Page<RestaurantWithDistance> restaurantsWithDistance = restaurantQueryRepository.getRestaurantsWithDistance(
                restaurantSearchCond, locationSearchCond, pageable
        );
        List<Long> restaurantIds = extractRestaurantIds(restaurantsWithDistance);
        List<Video> videos = videoRepository.findAllByRestaurantIdIn(restaurantIds);
        Map<Long, List<Celeb>> celebs = mapVideoToCeleb(groupingVideoByRestaurant(videos));
        List<RestaurantImage> images = restaurantImageRepository.findAllByRestaurantIdIn(restaurantIds);
        Map<Long, List<RestaurantImage>> restaurantListMap = groupingImageByRestaurant(images);
        List<RestaurantIdWithCount> restaurantIdWithCounts =
                restaurantLikeRepository.countLikesByRestaurantIds(restaurantIds);
        Map<Long, Integer> likeCounts = groupingLikeCountByRestaurant(restaurantIdWithCounts, restaurantIds);
        List<RestaurantQueryResponse> responseList =
                toResponseList(restaurantsWithDistance, celebs, restaurantListMap, likedRestaurantIds, likeCounts);
        return new PageImpl<>(responseList, pageable, restaurantsWithDistance.getTotalElements());
    }

    private List<RestaurantQueryResponse> toResponseList(
            Page<RestaurantWithDistance> restaurants,
            Map<Long, List<Celeb>> celebs,
            Map<Long, List<RestaurantImage>> images,
            Set<Long> likedRestaurantIds,
            Map<Long, Integer> likeCounts
    ) {
        return restaurants.getContent().stream()
                .map(restaurant -> toResponse(
                        celebs.get(restaurant.id()),
                        images.get(restaurant.id()),
                        restaurant,
                        likedRestaurantIds,
                        likeCounts.get(restaurant.id())
                )).toList();
    }

    private RestaurantQueryResponse toResponse(
            List<Celeb> celebs,
            List<RestaurantImage> images,
            RestaurantWithDistance restaurantWithDistance,
            Set<Long> likedRestaurantIds,
            Integer likeCount
    ) {
        if (likedRestaurantIds.contains(restaurantWithDistance.id())) {
            return RestaurantQueryResponse.from(restaurantWithDistance, celebs, images, true, likeCount);
        }
        return RestaurantQueryResponse.from(restaurantWithDistance, celebs, images, false, likeCount);
    }

    public List<RestaurantLikeQueryResponse> findAllByMemberId(Long memberId) {
        OauthMember member = oauthMemberRepository.getById(memberId);
        List<RestaurantLike> restaurantLikes = restaurantLikeRepository.findAllByMember(member);
        List<Restaurant> restaurants = extractRestaurants(restaurantLikes);
        List<Video> videos = videoRepository.findAllByRestaurantIn(restaurants);
        Map<Long, List<Celeb>> celebs = mapVideoToCeleb(groupingVideoByRestaurant(videos));
        List<RestaurantImage> images = restaurantImageRepository.findAllByRestaurantIn(restaurants);
        Map<Long, List<RestaurantImage>> restaurantListMap = groupingImageByRestaurant(images);
        return toResponseList(restaurants, celebs, restaurantListMap);
    }

    private List<Restaurant> extractRestaurants(List<RestaurantLike> restaurantLikes) {
        return restaurantLikes.stream()
                .map(RestaurantLike::restaurant)
                .toList();
    }

    private List<RestaurantLikeQueryResponse> toResponseList(
            List<Restaurant> restaurants,
            Map<Long, List<Celeb>> celebs,
            Map<Long, List<RestaurantImage>> images
    ) {
        return restaurants.stream()
                .map(restaurant -> toResponse(celebs.get(restaurant.id()), images.get(restaurant.id()), restaurant))
                .toList();
    }

    private RestaurantLikeQueryResponse toResponse(
            List<Celeb> celebs,
            List<RestaurantImage> images,
            Restaurant restaurant
    ) {
        return RestaurantLikeQueryResponse.from(restaurant, celebs, images);
    }

    public RestaurantDetailQueryResponse findRestaurantDetailById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        List<RestaurantImage> restaurantImages = restaurantImageRepository.findAllByRestaurant(restaurant);
        int likeCount = restaurantLikeRepository.countByRestaurant(restaurant);
        List<Celeb> celebs = getCelebsByRestaurant(restaurant);
        return RestaurantDetailQueryResponse.from(
                restaurant,
                celebs,
                restaurantImages,
                likeCount
        );
    }

    private List<Celeb> getCelebsByRestaurant(Restaurant restaurant) {
        return videoRepository.findAllByRestaurant(restaurant)
                .stream()
                .map(Video::celeb)
                .toList();
    }

    public Page<RestaurantQueryResponse> findAllNearByDistanceWithoutSpecificRestaurant(
            int distance,
            long restaurantId,
            Pageable pageable
    ) {
        Restaurant specificRestaurant = restaurantRepository.getById(restaurantId);
        Page<RestaurantWithDistance> restaurantsWithDistance = restaurantQueryRepository.getRestaurantsWithDistanceNearBy(
                distance,
                specificRestaurant,
                pageable
        );
        Page<RestaurantWithDistance> result = removeSpecificRestaurant(pageable, restaurantsWithDistance, specificRestaurant);
        return toRestaurantQueryResponsesPage(pageable, result);
    }

    private Page<RestaurantWithDistance> removeSpecificRestaurant(
            Pageable pageable,
            Page<RestaurantWithDistance> restaurantsWithDistance,
            Restaurant restaurant
    ) {
        List<RestaurantWithDistance> newContent = new ArrayList<>(restaurantsWithDistance.getContent());
        newContent.stream()
                .filter(restaurantWithDistance -> restaurantWithDistance.name().equals(restaurant.name()))
                .findFirst()
                .ifPresent(newContent::remove);
        return new PageImpl<>(newContent, pageable, restaurantsWithDistance.getTotalElements());
    }
}
