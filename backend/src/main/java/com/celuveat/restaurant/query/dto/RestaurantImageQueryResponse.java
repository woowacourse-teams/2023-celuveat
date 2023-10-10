package com.celuveat.restaurant.query.dto;

import com.celuveat.common.util.Base64Util;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.SocialMedia;

public record RestaurantImageQueryResponse(
        Long restaurantId,
        Long id,
        String name,
        String author,
        String sns
) {

    public RestaurantImageQueryResponse(
            Long restaurantId,
            Long id,
            String name,
            String author,
            SocialMedia socialMedia
    ) {
        this(restaurantId, id, name, author, socialMedia.name());
    }

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
