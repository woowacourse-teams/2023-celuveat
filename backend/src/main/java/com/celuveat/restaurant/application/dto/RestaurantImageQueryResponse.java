package com.celuveat.restaurant.application.dto;

import com.celuveat.common.util.Base64Util;
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
                Base64Util.encode(restaurantImage.name().replace(".png", "")) + ".png",
                restaurantImage.author(),
                restaurantImage.author()
        );
    }
}
