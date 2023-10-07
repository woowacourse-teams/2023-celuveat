package com.celuveat.restaurant.query.dto;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record LikedRestaurantQueryResponse(
        Long id,
        String name,
        String category,
        String roadAddress,
        @JsonProperty("lat") Double latitude,
        @JsonProperty("lng") Double longitude,
        String phoneNumber,
        String naverMapUrl,
        List<CelebQueryResponse> celebs,
        List<RestaurantImageQueryResponse> images
) {

    public static LikedRestaurantQueryResponse from(
            Restaurant restaurant,
            List<CelebQueryResponse> celebs,
            List<RestaurantImageQueryResponse> images
    ) {
        return new LikedRestaurantQueryResponse(
                restaurant.id(),
                restaurant.name(),
                restaurant.category(),
                restaurant.roadAddress(),
                restaurant.latitude(),
                restaurant.longitude(),
                restaurant.phoneNumber(),
                restaurant.naverMapUrl(),
                celebs,
                images
        );
    }
}
