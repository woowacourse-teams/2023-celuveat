package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.application.dto.CelebQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.Restaurant;
import java.util.List;

public class RestaurantFixture {

    public static Restaurant 음식점(String name) {
        return Restaurant.builder()
                .name(name)
                .roadAddress(name)
                .naverMapUrl(name)
                .phoneNumber(name)
                .category("category:" + name)
                .latitude(37.5206993d)
                .longitude(127.019975d)
                .build();
    }

    public static Restaurant 음식점(String name, Double latitude, Double longitude) {
        return Restaurant.builder()
                .name(name)
                .roadAddress(name)
                .naverMapUrl(name)
                .phoneNumber(name)
                .category("category:" + name)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public static boolean isCelebVisited(Long celebId, RestaurantQueryResponse restaurantQueryResponse) {
        List<Long> celebIds = restaurantQueryResponse.celebs().stream().map(CelebQueryResponse::id).toList();
        return celebIds.contains(celebId);
    }
}
