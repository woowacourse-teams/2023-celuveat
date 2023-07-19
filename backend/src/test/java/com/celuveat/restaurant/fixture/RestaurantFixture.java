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
                .latitude("37.5206993")
                .longitude("127.019975")
                .build();
    }
}
