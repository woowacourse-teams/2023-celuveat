package com.celuveat.restaurant.application.dto;

import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.video.domain.Video;
import java.util.List;

public record RestaurantDetailQueryResponse(
        Long id,
        String name,
        String category,
        String roadAddress,
        Double latitude,
        Double longitude,
        String phoneNumber,
        String naverMapUrl,
        Integer likeCount,
        Integer viewCount,
        List<RestaurantImageQueryResponse> imageUrls,
        List<VideoWithCelebQueryResponse> videos
) {

    public static RestaurantDetailQueryResponse of(
            Restaurant restaurant,
            List<RestaurantImage> restaurantImages,
            int likeCount,
            List<Video> videos
    ) {
        return new RestaurantDetailQueryResponse(
                restaurant.id(),
                restaurant.name(),
                restaurant.category(),
                restaurant.roadAddress(),
                restaurant.latitude(),
                restaurant.longitude(),
                restaurant.phoneNumber(),
                restaurant.naverMapUrl(),
                likeCount,
                0, //TODO view count
                restaurantImages.stream().map(RestaurantImageQueryResponse::of).toList(),
                videos.stream().map(VideoWithCelebQueryResponse::from).toList()
        );
    }
}
