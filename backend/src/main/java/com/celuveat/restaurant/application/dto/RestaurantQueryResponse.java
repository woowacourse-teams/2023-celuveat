package com.celuveat.restaurant.application.dto;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import java.util.List;

public record RestaurantQueryResponse(
        Long id,
        String name,
        String category,
        String roadAddress,
        Double latitude,
        Double longitude,
        String phoneNumber,
        String naverMapUrl,
        List<CelebQueryResponse> celebs,
        List<RestaurantImageQueryResponse> images
) {

    public static RestaurantQueryResponse from(
            Restaurant restaurant,
            List<Celeb> celebs,
            List<RestaurantImage> restaurantImages
    ) {
        return new RestaurantQueryResponse(
                restaurant.id(),
                restaurant.name(),
                restaurant.category(),
                restaurant.roadAddress(),
                Double.valueOf(restaurant.latitude()),
                Double.valueOf(restaurant.longitude()),
                restaurant.phoneNumber(),
                restaurant.naverMapUrl(),
                celebs.stream().map(CelebQueryResponse::of).toList(),
                restaurantImages.stream().map(RestaurantImageQueryResponse::of).toList()
        );
    }
}
