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
        Integer viewCount,
        Integer distance,
        Boolean isLiked,
        Integer likeCount,
        List<CelebQueryResponse> celebs,
        List<RestaurantImageQueryResponse> images
) {

    public static RestaurantQueryResponse from(
            RestaurantWithDistance restaurant,
            List<Celeb> celebs,
            List<RestaurantImage> restaurantImages,
            boolean isLiked,
            Integer likeCount
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
                restaurant.viewCount(),
                restaurant.distance().intValue(),
                isLiked,
                likeCount,
                celebs.stream().map(CelebQueryResponse::of).toList(),
                restaurantImages.stream().map(RestaurantImageQueryResponse::of).toList()
        );
    }

    public static RestaurantQueryResponse from(
            RestaurantQueryResponse other,
            List<CelebQueryResponse> celebs,
            List<RestaurantImageQueryResponse> restaurantImages
    ) {
        return new RestaurantQueryResponse(
                other.id,
                other.name,
                other.category,
                other.roadAddress,
                other.latitude,
                other.longitude,
                other.phoneNumber,
                other.naverMapUrl,
                other.viewCount,
                other.distance,
                other.isLiked,
                other.likeCount,
                celebs,
                restaurantImages
        );
    }
}
