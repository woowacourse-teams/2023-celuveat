package com.celuveat.restaurant.query.dao;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dao.support.RestaurantImageQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantLikeQueryDaoSupport;
import com.celuveat.restaurant.query.dto.RestaurantIdWithLikeCount;
import com.celuveat.restaurant.query.dto.RestaurantSimpleResponse;
import com.celuveat.restaurant.query.dto.RestaurantWithDistance;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.query.dao.VideoQueryDaoSupport;
import io.micrometer.common.lang.Nullable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantSimpleResponseDao {

    private final RestaurantWithDistanceDao restaurantWithDistanceDao;

    private final RestaurantLikeQueryDaoSupport restaurantLikeQueryDaoSupport;
    private final VideoQueryDaoSupport videoQueryDaoSupport;
    private final RestaurantImageQueryDaoSupport restaurantImageQueryDaoSupport;

    public Page<RestaurantSimpleResponse> findAllWithMemberLiked(
            RestaurantSearchCond restaurantCond,
            LocationSearchCond locationCond,
            Pageable pageable,
            Long memberId
    ) {
        Page<RestaurantWithDistance> restaurantsWithDistance = restaurantWithDistanceDao.getRestaurantsWithDistance(
                restaurantCond, locationCond, pageable
        );
        return mapToRestaurantWithCelebAndImagesSimpleResponse(restaurantsWithDistance, memberId);
    }

    public Page<RestaurantSimpleResponse> findAllNearByDistanceWithoutSpecificRestaurant(
            int distance,
            long restaurantId,
            @Nullable Long memberId,
            Pageable pageable
    ) {
        Page<RestaurantWithDistance> restaurantsWithDistance
                = restaurantWithDistanceDao.getRestaurantsNearByRestaurantId(distance, restaurantId,
                pageable);
        return mapToRestaurantWithCelebAndImagesSimpleResponse(restaurantsWithDistance, memberId);
    }

    private Page<RestaurantSimpleResponse> mapToRestaurantWithCelebAndImagesSimpleResponse(
            Page<RestaurantWithDistance> restaurants, @Nullable Long memberId
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
        List<RestaurantImage> images = restaurantImageQueryDaoSupport.findAllByRestaurantIdIn(restaurantIds);
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

    private Set<Long> getRestaurantIds(@Nullable Long memberId) {
        if (memberId == null) {
            return Collections.emptySet();
        }
        return restaurantLikeQueryDaoSupport.findAllByMemberId(memberId)
                .stream()
                .map(RestaurantLike::restaurant)
                .map(Restaurant::id)
                .collect(toUnmodifiableSet());
    }

    private Map<Long, Long> likeCountGroupByRestaurantIds(List<Long> restaurantIds) {
        return restaurantLikeQueryDaoSupport.likeCountGroupByRestaurantsId(restaurantIds)
                .stream()
                .collect(toMap(
                        RestaurantIdWithLikeCount::restaurantId,
                        RestaurantIdWithLikeCount::count
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
