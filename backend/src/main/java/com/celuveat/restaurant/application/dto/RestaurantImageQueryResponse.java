package com.celuveat.restaurant.application.dto;

import com.celuveat.restaurant.domain.RestaurantImage;

public record RestaurantImageQueryResponse(
        Long id,
        String name,
        String author,
        String sns
) {
    
    public static RestaurantImageQueryResponse of(RestaurantImage restaurantImage) {
        return new RestaurantImageQueryResponse(
                restaurantImage.id(),
                restaurantImage.name(),
                restaurantImage.author(),
                restaurantImage.author()
        );
    }
}
