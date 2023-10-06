package com.celuveat.restaurant.query.dao;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.query.dao.support.RestaurantImageQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantLikeQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantQueryDaoSupport;
import com.celuveat.restaurant.query.dto.RestaurantDetailResponse;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.query.dao.VideoQueryDaoSupport;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantDetailResponseDao {

    private final RestaurantLikeQueryDaoSupport restaurantLikeQueryDaoSupport;
    private final RestaurantQueryDaoSupport restaurantQueryDaoSupport;
    private final VideoQueryDaoSupport videoQueryDaoSupport;
    private final RestaurantImageQueryDaoSupport restaurantImageQueryDaoSupport;

    public RestaurantDetailResponse findRestaurantDetailById(Long restaurantId, @Nullable Long memberId) {
        Restaurant restaurant = restaurantQueryDaoSupport.getById(restaurantId);
        return mapToRestaurantWithCelebAndImagesDetailResponse(restaurant, memberId);
    }

    private RestaurantDetailResponse mapToRestaurantWithCelebAndImagesDetailResponse(
            Restaurant restaurant,
            @Nullable Long memberId
    ) {
        List<Celeb> celebs = getCelebsByRestaurant(restaurant);
        List<RestaurantImage> restaurantImages = restaurantImageQueryDaoSupport.findAllByRestaurant(restaurant);
        int likeCount = restaurantLikeQueryDaoSupport.countByRestaurant(restaurant);
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

    private boolean applyLikedRestaurant(Restaurant restaurant, @Nullable Long memberId) {
        if (memberId == null) {
            return false;
        }
        return restaurantLikeQueryDaoSupport.findByRestaurantAndMemberId(restaurant, memberId)
                .isPresent();
    }
}
