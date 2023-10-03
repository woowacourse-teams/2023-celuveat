package com.celuveat.restaurant.query.dao;

import static com.celuveat.common.util.StreamUtil.groupBySameOrder;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.common.domain.BaseEntity;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.AddressSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dao.support.RestaurantImageQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantLikeQueryDaoSupport;
import com.celuveat.restaurant.query.dto.RestaurantSimpleResponse;
import com.celuveat.restaurant.query.dto.RestaurantWithDistance;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.query.dao.VideoQueryDaoSupport;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
        Page<RestaurantWithDistance> restaurants =
                restaurantWithDistanceDao.search(restaurantCond, locationCond, pageable);
        return toSimpleResponse(memberId, restaurants);
    }

    public Page<RestaurantSimpleResponse> findAllNearByDistanceWithoutSpecificRestaurant(
            long restaurantId,
            int distance,
            @Nullable Long memberId,
            Pageable pageable
    ) {
        Page<RestaurantWithDistance> restaurants =
                restaurantWithDistanceDao.searchNearBy(restaurantId, distance, pageable);
        return toSimpleResponse(memberId, restaurants);
    }

    public Page<RestaurantSimpleResponse> findAllByAddress(
            AddressSearchCond addressSearchCond,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        Page<RestaurantWithDistance> restaurants =
                restaurantWithDistanceDao.searchByAddress(addressSearchCond, pageable);
        return toSimpleResponse(memberId, restaurants);
    }

    private Page<RestaurantSimpleResponse> toSimpleResponse(
            @Nullable Long memberId, Page<RestaurantWithDistance> restaurants
    ) {
        List<Long> restaurantIds = restaurants.map(RestaurantWithDistance::id).toList();
        Map<Long, List<Celeb>> celebsMap = getCelebsGroupByRestaurantsId(restaurantIds);
        Map<Long, List<RestaurantImage>> restaurantImageMap = getImagesGroupByRestaurantsId(restaurantIds);
        Map<Long, Boolean> isLikedMap = getIsLikedGroupByRestaurantsId(memberId, restaurantIds);
        return RestaurantSimpleResponse.of(
                restaurants, celebsMap, restaurantImageMap, isLikedMap
        );
    }

    private Map<Long, Boolean> getIsLikedGroupByRestaurantsId(@Nullable Long memberId, List<Long> restaurantIds) {
        Set<Long> likedRestaurantIds = restaurantLikeQueryDaoSupport
                .findAllByMemberIdAndRestaurantIdIn(memberId, restaurantIds)
                .stream()
                .map(RestaurantLike::restaurant)
                .map(BaseEntity::id)
                .collect(toSet());
        return restaurantIds.stream()
                .collect(toMap(identity(), likedRestaurantIds::contains));
    }

    private Map<Long, List<Celeb>> getCelebsGroupByRestaurantsId(List<Long> restaurantIds) {
        List<Video> videos = videoQueryDaoSupport.findAllByRestaurantIdIn(restaurantIds);
        Map<Long, List<Video>> restaurantVideos = groupBySameOrder(videos, video -> video.restaurant().id());
        return restaurantVideos.entrySet()
                .stream()
                .map(it -> Map.entry(it.getKey(), videosToCelebs(it)))
                .collect(toMap(
                        Entry::getKey,
                        Entry::getValue
                ));
    }

    private Map<Long, List<RestaurantImage>> getImagesGroupByRestaurantsId(List<Long> restaurantIds) {
        List<RestaurantImage> images = restaurantImageQueryDaoSupport.findAllByRestaurantIdIn(restaurantIds);
        return groupBySameOrder(images, image -> image.restaurant().id());
    }

    private List<Celeb> videosToCelebs(Entry<Long, List<Video>> idWithVideosEntry) {
        return idWithVideosEntry.getValue()
                .stream()
                .map(Video::celeb)
                .toList();
    }
}
