package com.celuveat.restaurant.application.dto;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.restaurant.domain.dto.RestaurantWithDistance;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record RestaurantQueryResponse(
        Long id,
        String name,
        String category,
        String roadAddress,
        @JsonProperty("lat") Double latitude,
        @JsonProperty("lng") Double longitude,
        String phoneNumber,
        String naverMapUrl,
        Integer distance,
        Boolean isLiked,
        Integer likeCount,
        Integer viewCount,
        List<CelebQueryResponse> celebs,
        List<RestaurantImageQueryResponse> images
) {

    public static RestaurantQueryResponse from(
            RestaurantWithDistance restaurant,
            List<Celeb> celebs,
            List<RestaurantImage> restaurantImages
    ) {
        return new RestaurantQueryResponse(
                restaurant.id(),
                restaurant.name(),
                restaurant.category(),
                restaurant.roadAddress(),
                restaurant.latitude(),
                restaurant.longitude(),
                restaurant.phoneNumber(),
                restaurant.naverMapUrl(),
                restaurant.distance().intValue(),
                false,
                null,
                null,
                celebs.stream().map(CelebQueryResponse::of).toList(),
                restaurantImages.stream().map(RestaurantImageQueryResponse::of).toList()
        );
    }

    public static RestaurantQueryResponse from(
            RestaurantWithDistance restaurant,
            List<Celeb> celebs,
            List<RestaurantImage> restaurantImages,
            boolean isLiked
    ) {
        return new RestaurantQueryResponse(
                restaurant.id(),
                restaurant.name(),
                restaurant.category(),
                restaurant.roadAddress(),
                restaurant.latitude(),
                restaurant.longitude(),
                restaurant.phoneNumber(),
                restaurant.naverMapUrl(),
                restaurant.distance().intValue(),
                isLiked,
                null,
                null,
                celebs.stream().map(CelebQueryResponse::of).toList(),
                restaurantImages.stream().map(RestaurantImageQueryResponse::of).toList()
        );
    }
}
