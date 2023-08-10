package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.application.dto.CelebQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.Restaurant;
import java.util.List;

public class RestaurantFixture {

    public static final Restaurant 국민연금_구내식당 = Restaurant.builder()
            .name("국민연금")
            .roadAddress("국민연금 주소")
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
                .latitude(latitude)
                .longitude(longitude)
                .viewCount(0)
                .build();
    }

    public static boolean isCelebVisited(Long celebId, RestaurantQueryResponse restaurantQueryResponse) {
        List<Long> celebIds = restaurantQueryResponse.celebs().stream().map(CelebQueryResponse::id).toList();
        return celebIds.contains(celebId);
    }
}
