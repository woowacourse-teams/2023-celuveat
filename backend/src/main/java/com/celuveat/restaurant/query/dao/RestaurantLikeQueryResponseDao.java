package com.celuveat.restaurant.query.dao;

import static com.celuveat.common.util.StreamUtil.sameOrderGroupBy;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.query.dao.support.RestaurantImageQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantLikeQueryDaoSupport;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.query.dao.VideoQueryDaoSupport;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantLikeQueryResponseDao {

    private final VideoQueryDaoSupport videoQueryDaoSupport;
    private final RestaurantLikeQueryDaoSupport restaurantLikeQueryDaoSupport;
    private final RestaurantImageQueryDaoSupport restaurantImageQueryDaoSupport;

    public List<LikedRestaurantQueryResponse> findAllLikedRestaurantByMemberId(Long memberId) {
        List<Restaurant> restaurants = getLikedRestaurants(memberId);
        Map<Restaurant, List<Celeb>> celebsMap = celebsGroupByRestaurant(restaurants);
        Map<Restaurant, List<RestaurantImage>> restaurantMap = imagesGroupByRestaurants(restaurants);
        return restaurants.stream()
                .map(restaurant -> LikedRestaurantQueryResponse.of(restaurant, celebsMap, restaurantMap))
                .toList();
    }

    private List<Restaurant> getLikedRestaurants(Long memberId) {
        return restaurantLikeQueryDaoSupport.findAllByMemberIdOrderByCreatedDateDesc(memberId)
                .stream()
                .map(RestaurantLike::restaurant)
                .toList();
    }

    private Map<Restaurant, List<Celeb>> celebsGroupByRestaurant(List<Restaurant> restaurants) {
        List<Video> videos = videoQueryDaoSupport.findAllByRestaurantIn(restaurants);
        Map<Restaurant, List<Video>> restaurantVideos = sameOrderGroupBy(videos, Video::restaurant);
        Map<Restaurant, List<Celeb>> celebs = new LinkedHashMap<>();
        for (Restaurant restaurant : restaurantVideos.keySet()) {
            List<Celeb> list = restaurantVideos.get(restaurant).stream()
                    .map(Video::celeb)
                    .toList();
            celebs.put(restaurant, list);
        }
        return celebs;
    }

    private Map<Restaurant, List<RestaurantImage>> imagesGroupByRestaurants(List<Restaurant> restaurants) {
        return sameOrderGroupBy(
                restaurantImageQueryDaoSupport.findAllByRestaurantIn(restaurants),
                RestaurantImage::restaurant
        );
    }
}
