package com.celuveat.restaurant.application;

import static java.util.stream.Collectors.groupingBy;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.restaurant.domain.RestaurantQueryRepository;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.LocationSearchCond;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import com.celuveat.restaurant.domain.dto.RestaurantWithDistance;
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
        Page<RestaurantWithDistance> restaurantsWithDistance =
                restaurantQueryRepository.getRestaurantsWithDistance(restaurantSearchCond, locationSearchCond,
                        pageable);
        List<Long> restaurantIds = restaurantsWithDistance.getContent().stream()
                .map(RestaurantWithDistance::id)
                .toList();
        List<Video> videos = findVideoByRestaurantIdIn(restaurantIds);
        Map<Long, List<Celeb>> celebs = mapToCeleb(groupingVideoByRestaurant(videos));
        List<RestaurantImage> images = findImageByRestaurantIn(restaurantIds);
        Map<Long, List<RestaurantImage>> restaurantListMap = groupingImageByRestaurant(images);
        List<RestaurantQueryResponse> responseList = toResponseList(restaurantsWithDistance, celebs, restaurantListMap);
        return new PageImpl<>(responseList, pageable, restaurantsWithDistance.getTotalPages());
    }

    private List<Video> findVideoByRestaurantIdIn(List<Long> restaurantIds) {
        return videoRepository.findAllByRestaurantIdIn(restaurantIds);
    }

    private Map<Long, List<Video>> groupingVideoByRestaurant(List<Video> videos) {
        return videos.stream()
                .collect(groupingBy(video -> video.restaurant().id(), LinkedHashMap::new, Collectors.toList()));
    }

    private Map<Long, List<Celeb>> mapToCeleb(Map<Long, List<Video>> restaurantVideos) {
        Map<Long, List<Celeb>> celebs = new LinkedHashMap<>();
        for (Long restaurantId : restaurantVideos.keySet()) {
            List<Celeb> list = restaurantVideos.get(restaurantId).stream()
                    .map(Video::celeb)
                    .toList();
            celebs.put(restaurantId, list);
        }
        return celebs;
    }

    private List<RestaurantImage> findImageByRestaurantIn(List<Long> restaurantIds) {
        return restaurantImageRepository.findAllByRestaurantIdIn(restaurantIds);
    }

    private Map<Long, List<RestaurantImage>> groupingImageByRestaurant(List<RestaurantImage> images) {
        return images.stream()
                .collect(groupingBy(image -> image.restaurant().id(), LinkedHashMap::new, Collectors.toList()));
    }

    private List<RestaurantQueryResponse> toResponseList(
            Page<RestaurantWithDistance> restaurants,
            Map<Long, List<Celeb>> celebs,
            Map<Long, List<RestaurantImage>> images
    ) {
        return restaurants.getContent().stream()
                .map(restaurant -> toResponse(celebs.get(restaurant.id()), images.get(restaurant.id()), restaurant))
                .toList();
    }

    private RestaurantQueryResponse toResponse(
            List<Celeb> celebs,
            List<RestaurantImage> images,
            RestaurantWithDistance restaurantWithDistance
    ) {
        return RestaurantQueryResponse.from(restaurantWithDistance, celebs, images);
    }
}
