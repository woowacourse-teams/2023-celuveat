package com.celuveat.restaurant.query.dto;

import com.celuveat.common.util.Base64Util;
import com.celuveat.restaurant.command.domain.RestaurantImage;

public record RestaurantImageQueryResponse(
        Long id,
        String name,
        String author,
        String sns
) {

    public static RestaurantImageQueryResponse of(RestaurantImage restaurantImage) {
        return new RestaurantImageQueryResponse(
                restaurantImage.id(),
                Base64Util.encode(restaurantImage.name().replace(".jpeg", "")) + ".jpeg",
                restaurantImage.author(),
                restaurantImage.socialMedia().name()
        );
    }
}
