package com.celuveat.restaurant.query.dto;

import com.celuveat.common.util.Base64Util;
import com.celuveat.restaurant.command.domain.RestaurantImage;

public record RestaurantImageQueryResponse(
        Long restaurantId,
        Long id,
        String name,
        String author,
        String sns
) {

    public static RestaurantImageQueryResponse of(RestaurantImage restaurantImage) {
        return new RestaurantImageQueryResponse(
                restaurantImage.restaurant().id(),
                restaurantImage.id(),
                Base64Util.encode(restaurantImage.name()),
                restaurantImage.author(),
                restaurantImage.socialMedia().name()
        );
    }
}
