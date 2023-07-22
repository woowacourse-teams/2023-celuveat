package com.celuveat.restaurant.application;

import static java.util.stream.Collectors.groupingBy;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.restaurant.domain.RestaurantQueryRepository;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import com.celuveat.video.domain.Video;
import com.celuveat.video.domain.VideoRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantQueryService {

    private final RestaurantQueryRepository restaurantQueryRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final VideoRepository videoRepository;

    public List<RestaurantQueryResponse> findAll(RestaurantSearchCond cond) {
        List<Restaurant> restaurants = restaurantQueryRepository.getRestaurants(cond);
        List<Video> videos = findVideoByRestaurantIn(restaurants);
        Map<Restaurant, List<Celeb>> celebs = mapToCeleb(groupingVideoByRestaurant(videos));
        List<RestaurantImage> images = findImageByRestaurantIn(restaurants);
        Map<Restaurant, List<RestaurantImage>> restaurantListMap = groupingImageByRestaurant(images);
        return toResponseList(celebs, restaurantListMap);
    }

    private List<Video> findVideoByRestaurantIn(List<Restaurant> restaurants) {
        return videoRepository.findAllByRestaurantIn(restaurants);
    }

    private Map<Restaurant, List<Video>> groupingVideoByRestaurant(List<Video> videos) {
        return videos.stream()
                .collect(groupingBy(Video::restaurant, LinkedHashMap::new, Collectors.toList()));
    }

    private Map<Restaurant, List<Celeb>> mapToCeleb(Map<Restaurant, List<Video>> restaurantVideos) {
        Map<Restaurant, List<Celeb>> celebs = new LinkedHashMap<>();
        for (Restaurant restaurant : restaurantVideos.keySet()) {
            List<Celeb> list = restaurantVideos.get(restaurant).stream()
                    .map(Video::celeb)
                    .toList();
            celebs.put(restaurant, list);
        }
        return celebs;
    }

    private List<RestaurantImage> findImageByRestaurantIn(List<Restaurant> restaurants) {
        return restaurantImageRepository.findAllByRestaurantIn(restaurants);
    }

    private Map<Restaurant, List<RestaurantImage>> groupingImageByRestaurant(List<RestaurantImage> images) {
        return images.stream()
                .collect(groupingBy(RestaurantImage::restaurant, LinkedHashMap::new, Collectors.toList()));
    }

    private List<RestaurantQueryResponse> toResponseList(
            Map<Restaurant, List<Celeb>> celebs,
            Map<Restaurant, List<RestaurantImage>> images
    ) {
        return images.keySet().stream()
                .map(restaurant -> toResponse(celebs.get(restaurant), images.get(restaurant), restaurant))
                .toList();
    }

    private RestaurantQueryResponse toResponse(
            List<Celeb> celebs,
            List<RestaurantImage> images,
            Restaurant restaurant
    ) {
        return RestaurantQueryResponse.from(restaurant, celebs, images);
    }
}
