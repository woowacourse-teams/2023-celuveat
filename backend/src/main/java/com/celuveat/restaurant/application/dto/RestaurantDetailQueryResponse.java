package com.celuveat.restaurant.application.dto;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record RestaurantDetailQueryResponse(
        Long id,
        String name,
        String category,
        String roadAddress,
        @JsonProperty("lat") Double latitude,
        @JsonProperty("lng") Double longitude,
        String phoneNumber,
        String naverMapUrl,
        Integer likeCount,
        Integer viewCount,
        List<CelebQueryResponse> celebs,
        List<RestaurantImageQueryResponse> images
) {

    public static RestaurantDetailQueryResponse from(
            Restaurant restaurant,
            List<Celeb> celebs,
            List<RestaurantImage> restaurantImages,
            int likeCount
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
                celebs.stream().map(CelebQueryResponse::of).toList(),
                restaurantImages.stream().map(RestaurantImageQueryResponse::of).toList()
        );
    }

    public static RestaurantDetailQueryResponse from(
            RestaurantDetailQueryResponse other,
            List<CelebQueryResponse> celebs,
            List<RestaurantImageQueryResponse> restaurantImages
    ) {
        return new RestaurantDetailQueryResponse(
                other.id(),
                other.name(),
                other.category(),
                other.roadAddress(),
                other.latitude(),
                other.longitude(),
                other.phoneNumber(),
                other.naverMapUrl(),
                other.likeCount(),
                other.viewCount(), //TODO view count
                celebs,
                restaurantImages
        );
    }
}
