package com.celuveat.celuveat.restaurant.fixture;

import com.celuveat.celuveat.restaurant.domain.Category;
import com.celuveat.celuveat.restaurant.domain.Restaurant;

@SuppressWarnings("NonAsciiCharacters")
public class RestaurantFixture {

    public static Restaurant 교촌치킨() {
        return Restaurant.builder()
                .imageUrl("https://교촌.com")
                .name("교촌치킨")
                .address("서울특별시 도봉구 도봉로 123길 45")
                .category(Category.ETC)
                .rating("4.9")
                .phoneNumber("010-1234-5678")
                .build();
    }

    public static Restaurant 맥도날드() {
        return Restaurant.builder()
                .imageUrl("https://맥도.com")
                .name("맥도날드")
                .address("서울특별시 노원구 노원로 102길 43")
                .category(Category.ETC)
                .rating("4.2")
                .phoneNumber("010-1234-5328")
                .build();
    }
}
