package com.celuveat.celuveat.restaurant.fixture;

import com.celuveat.celuveat.restaurant.domain.Category;
import com.celuveat.celuveat.restaurant.domain.Restaurant;

public class RestaurantFixture {

    public static Restaurant 음식점() {
        return Restaurant.builder()
                .imageUrl("https://naver.com")
                .name("말랑이네 맛집")
                .address("서울특별시 도봉구 도봉로 123길 45")
                .category(Category.ETC)
                .rating("4.9")
                .phoneNumber("010-1234-5678")
                .build();
    }
}
