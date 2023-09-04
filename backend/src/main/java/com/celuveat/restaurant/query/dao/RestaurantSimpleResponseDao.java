package com.celuveat.restaurant.query.dao;

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
            @Nullable Long memberId
    ) {
        Page<RestaurantWithDistance> restaurants = restaurantWithDistanceDao.getRestaurantsWithDistance(
                restaurantCond, locationCond, pageable
        );
        return mapToSimpleResponse(restaurants, memberId);
    }

    public Page<RestaurantSimpleResponse> findAllNearByDistanceWithoutSpecificRestaurant(
            int distance,
            long restaurantId,
            @Nullable Long memberId,
            Pageable pageable
    ) {
        Page<RestaurantWithDistance> restaurants = restaurantWithDistanceDao.getRestaurantsNearByRestaurantId(
                distance, restaurantId, pageable
        );
        return mapToSimpleResponse(restaurants, memberId);
    }

    private Page<RestaurantSimpleResponse> mapToSimpleResponse(
            Page<RestaurantWithDistance> restaurants,
            @Nullable Long memberId
    ) {
        List<Long> restaurantIds = restaurants.map(RestaurantWithDistance::id).toList();
        List<Video> videos = videoQueryDaoSupport.findAllByRestaurantIdIn(restaurantIds);
        List<RestaurantImage> images = restaurantImageQueryDaoSupport.findAllByRestaurantIdIn(restaurantIds);
        Map<Long, List<Celeb>> celebsMap = mapToCelebsGroupByRestaurantId(videos);
        Map<Long, List<RestaurantImage>> restaurantMap = groupingImageByRestaurantId(images);
        Map<Long, Long> likeCountsGroupById = likeCountGroupByRestaurantId(restaurantIds);
        Set<Long> likedRestaurantIds = getRestaurantLikes(memberId);
        return RestaurantSimpleResponse.of(
                restaurants, celebsMap, restaurantMap,
                likedRestaurantIds, likeCountsGroupById);
    }

    private Map<Long, List<Celeb>> mapToCelebsGroupByRestaurantId(List<Video> videos) {
        Map<Long, List<Video>> restaurantVideos = groupByRestaurantId(videos);
        Map<Long, List<Celeb>> celebs = new LinkedHashMap<>();
        for (Long restaurantId : restaurantVideos.keySet()) {
            List<Celeb> list = restaurantVideos.get(restaurantId).stream()
                    .map(Video::celeb)
                    .toList();
            celebs.put(restaurantId, list);
        }
        return celebs;
    }

    private Map<Long, List<Video>> groupByRestaurantId(List<Video> videos) {
        return videos.stream()
                .collect(groupingBy(
                        video -> video.restaurant().id(),
                        LinkedHashMap::new,
                        toList()
                ));
    }

    private Map<Long, List<RestaurantImage>> groupingImageByRestaurantId(List<RestaurantImage> images) {
        return images.stream()
                .collect(groupingBy(
                        image -> image.restaurant().id(),
                        LinkedHashMap::new,
                        toList()
                ));
    }

    private Set<Long> getRestaurantLikes(@Nullable Long memberId) {
        if (memberId == null) {
            return Collections.emptySet();
        }
        return restaurantLikeQueryDaoSupport.findAllByMemberId(memberId)
                .stream()
                .map(RestaurantLike::restaurant)
                .map(Restaurant::id)
                .collect(toUnmodifiableSet());
    }

    private Map<Long, Long> likeCountGroupByRestaurantId(List<Long> restaurantIds) {
        return restaurantLikeQueryDaoSupport.likeCountGroupByRestaurantsId(restaurantIds)
                .stream()
                .collect(toMap(
                        RestaurantIdWithLikeCount::restaurantId,
                        RestaurantIdWithLikeCount::count
                ));
    }
}
