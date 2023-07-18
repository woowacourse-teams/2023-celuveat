package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.domain.Restaurant;

public class RestaurantFixture {

    public static Restaurant 음식점(String name) {
        return Restaurant.builder()
                .name(name)
                .roadAddress(name)
                .naverMapUrl(name)
                .phoneNumber(name)
                .category(name)
                .latitude(name)
                .longitude(name)
                .build();
    }
}
