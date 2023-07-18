package com.celuveat.restaurant.application;

import static java.util.stream.Collectors.groupingBy;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.video.domain.Video;
import com.celuveat.video.domain.VideoRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantQueryService {

    private final RestaurantImageRepository restaurantImageRepository;
    private final VideoRepository videoRepository;

    public List<RestaurantQueryResponse> findAll() {
        Map<Restaurant, List<Celeb>> celebs = restaurantCelebsMap();
        Map<Restaurant, List<RestaurantImage>> images = restaurantImagesMap();
        return images.keySet().stream()
                .map(restaurant -> mapToRestaurantQueryResponse(celebs, images, restaurant))
                .toList();
    }

    private Map<Restaurant, List<Celeb>> restaurantCelebsMap() {
        List<Video> videos = videoRepository.findAll();
        Map<Restaurant, List<Video>> restaurantVideos = videos.stream()
                .collect(groupingBy(Video::restaurant, LinkedHashMap::new, Collectors.toList()));
        Map<Restaurant, List<Celeb>> celebs = new LinkedHashMap<>();
        for (Restaurant restaurant : restaurantVideos.keySet()) {
            List<Celeb> list = restaurantVideos.get(restaurant).stream()
                    .map(Video::celeb)
                    .toList();
            celebs.put(restaurant, list);
        }
        return celebs;
    }

    private Map<Restaurant, List<RestaurantImage>> restaurantImagesMap() {
        List<RestaurantImage> restaurantImages = restaurantImageRepository.findAll();
        return restaurantImages.stream()
                .collect(groupingBy(RestaurantImage::restaurant, LinkedHashMap::new, Collectors.toList()));
    }

    private RestaurantQueryResponse mapToRestaurantQueryResponse(
            Map<Restaurant, List<Celeb>> restaurantCelebsMap,
            Map<Restaurant, List<RestaurantImage>> restaurantImagesMap,
            Restaurant restaurant
    ) {
        return RestaurantQueryResponse.from(restaurant, restaurantCelebsMap.get(restaurant),
                restaurantImagesMap.get(restaurant));
    }
}
