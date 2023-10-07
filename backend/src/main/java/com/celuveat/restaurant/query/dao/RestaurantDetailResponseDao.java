package com.celuveat.restaurant.query.dao;

import com.celuveat.common.dao.Dao;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.query.dao.support.RestaurantImageQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantLikeQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantQueryDaoSupport;
import com.celuveat.restaurant.query.dto.CelebQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.query.dto.RestaurantImageQueryResponse;
import com.celuveat.video.query.dao.VideoQueryDaoSupport;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Dao
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantDetailResponseDao {

    private final RestaurantLikeQueryDaoSupport restaurantLikeQueryDaoSupport;
    private final RestaurantQueryDaoSupport restaurantQueryDaoSupport;
    private final VideoQueryDaoSupport videoQueryDaoSupport;
    private final RestaurantImageQueryDaoSupport restaurantImageQueryDaoSupport;

    public RestaurantDetailResponse find(Long restaurantId, @Nullable Long memberId) {
        Restaurant restaurant = restaurantQueryDaoSupport.getById(restaurantId);
        return toResponseWithCelebAndImagesAndLiked(restaurant, memberId);
    }

    private RestaurantDetailResponse toResponseWithCelebAndImagesAndLiked(
            Restaurant restaurant,
            @Nullable Long memberId
    ) {
        List<CelebQueryResponse> celebs = getCelebsByRestaurant(restaurant);
        List<RestaurantImageQueryResponse> restaurantImages = restaurantImageQueryDaoSupport
                .findAllByRestaurant(restaurant)
                .stream()
                .map(RestaurantImageQueryResponse::of)
                .toList();
        int likeCount = restaurantLikeQueryDaoSupport.countByRestaurant(restaurant);
        return RestaurantDetailResponse.builder()
                .restaurant(restaurant)
                .celebs(celebs)
                .restaurantImages(restaurantImages)
                .likeCount(likeCount)
                .isLiked(applyLikedRestaurant(restaurant, memberId))
                .build();
    }

    private List<CelebQueryResponse> getCelebsByRestaurant(Restaurant restaurant) {
        return videoQueryDaoSupport.findAllByRestaurant(restaurant).stream()
                .map(it -> CelebQueryResponse.from(restaurant.id(), it.celeb()))
                .toList();
    }

    private boolean applyLikedRestaurant(Restaurant restaurant, @Nullable Long memberId) {
        if (memberId == null) {
            return false;
        }
        return restaurantLikeQueryDaoSupport.findByRestaurantAndMemberId(restaurant, memberId)
                .isPresent();
    }
}
