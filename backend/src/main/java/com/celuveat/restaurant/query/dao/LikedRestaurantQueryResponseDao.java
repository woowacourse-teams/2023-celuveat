package com.celuveat.restaurant.query.dao;

import static com.celuveat.common.util.StreamUtil.groupBySameOrder;

import com.celuveat.common.dao.Dao;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.query.dao.support.RestaurantImageQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantLikeQueryDaoSupport;
import com.celuveat.restaurant.query.dto.CelebQueryResponse;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantImageQueryResponse;
import com.celuveat.video.query.dao.support.VideoQueryDaoSupport;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Dao
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikedRestaurantQueryResponseDao {

    private final VideoQueryDaoSupport videoQueryDaoSupport;
    private final RestaurantLikeQueryDaoSupport restaurantLikeQueryDaoSupport;
    private final RestaurantImageQueryDaoSupport restaurantImageQueryDaoSupport;

    public List<LikedRestaurantQueryResponse> findLikedByMemberId(Long memberId) {
        List<Restaurant> restaurants = getLikedRestaurants(memberId);
        Map<Long, List<CelebQueryResponse>> celebs = celebsGroupByRestaurant(restaurants);
        Map<Long, List<RestaurantImageQueryResponse>> images = imagesGroupByRestaurants(restaurants);
        return restaurants.stream()
                .map(it ->
                        LikedRestaurantQueryResponse.from(it, celebs.get(it.id()), images.get(it.id()))
                ).toList();
    }

    private List<Restaurant> getLikedRestaurants(Long memberId) {
        return restaurantLikeQueryDaoSupport.findAllByMemberIdOrderByCreatedDateDesc(memberId)
                .stream()
                .map(RestaurantLike::restaurant)
                .toList();
    }

    private Map<Long, List<CelebQueryResponse>> celebsGroupByRestaurant(List<Restaurant> restaurants) {
        List<CelebQueryResponse> celebs = videoQueryDaoSupport.findAllByRestaurantIn(restaurants)
                .stream()
                .map(it -> CelebQueryResponse.from(it.restaurant().id(), it.celeb()))
                .toList();
        return groupBySameOrder(celebs, CelebQueryResponse::restaurantId);
    }

    private Map<Long, List<RestaurantImageQueryResponse>> imagesGroupByRestaurants(List<Restaurant> restaurants) {
        List<RestaurantImageQueryResponse> images = restaurantImageQueryDaoSupport.findAllByRestaurantIn(restaurants)
                .stream()
                .map(RestaurantImageQueryResponse::of)
                .toList();
        return groupBySameOrder(images, RestaurantImageQueryResponse::restaurantId);
    }
}
