package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.command.domain.Restaurant;

public class RestaurantFixture {

    public static final Restaurant 국민연금_구내식당 = Restaurant.builder()
            .name("국민연금")
            .roadAddress("국민연금 주소")
            .superCategory("한식")
            .category("한식")
            .latitude(12.3456)
            .longitude(12.3456)
            .phoneNumber("국민연금 번호")
            .naverMapUrl("국민연금 네이버지도 링크")
            .viewCount(0)
            .build();

    public static Restaurant 음식점(String name) {
        return Restaurant.builder()
                .name(name)
                .roadAddress(name)
                .naverMapUrl(name)
                .phoneNumber(name)
                .category("category:" + name)
                .superCategory("superCategory:" + name)
                .latitude(37.5206993d)
                .longitude(127.019975d)
                .viewCount(0)
                .build();
    }

    public static Restaurant 음식점(String name, Double latitude, Double longitude) {
        return Restaurant.builder()
                .name(name)
                .roadAddress(name)
                .naverMapUrl(name)
                .phoneNumber(name)
                .category("category:" + name)
                .superCategory("superCategory:" + name)
                .latitude(latitude)
                .longitude(longitude)
                .viewCount(0)
                .build();
    }
}
