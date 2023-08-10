package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.celuveat.restaurant.domain.SocialMedia;

public class RestaurantImageFixture {

    public static RestaurantImage 음식점사진(String name, Restaurant 음식점, String author) {
        return RestaurantImage.builder()
                .name(name)
                .socialMedia(SocialMedia.INSTAGRAM)
                .author(author)
                .restaurant(음식점)
                .build();
    }
}
