package com.celuveat.restaurant.application;

import static java.util.stream.Collectors.groupingBy;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.restaurant.domain.RestaurantQueryRepository;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.LocationSearchCond;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import com.celuveat.video.domain.Video;
import com.celuveat.video.domain.VideoRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    private final RestaurantQueryRepository restaurantQueryRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final VideoRepository videoRepository;

    public Page<RestaurantQueryResponse> findAll(
            RestaurantSearchCond restaurantSearchCond,
            LocationSearchCond locationSearchCond,
            Pageable pageable
    ) {
        Page<Restaurant> restaurants =
                restaurantQueryRepository.getRestaurants(restaurantSearchCond, locationSearchCond, pageable);
        List<Video> videos = findVideoByRestaurantIn(restaurants.getContent());
        Map<Restaurant, List<Celeb>> celebs = mapToCeleb(groupingVideoByRestaurant(videos));
        List<RestaurantImage> images = findImageByRestaurantIn(restaurants.getContent());
        Map<Restaurant, List<RestaurantImage>> restaurantListMap = groupingImageByRestaurant(images);
        List<RestaurantQueryResponse> responseList = toResponseList(restaurants, celebs, restaurantListMap);
        return new PageImpl<>(responseList, pageable, restaurants.getTotalPages());
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
            Page<Restaurant> restaurants,
            Map<Restaurant, List<Celeb>> celebs,
            Map<Restaurant, List<RestaurantImage>> images
    ) {
        return restaurants.getContent().stream()
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
