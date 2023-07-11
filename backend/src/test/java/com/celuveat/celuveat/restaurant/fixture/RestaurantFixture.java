package com.celuveat.celuveat.restaurant.fixture;

import com.celuveat.celuveat.restaurant.domain.Category;
import com.celuveat.celuveat.restaurant.domain.Restaurant;

@SuppressWarnings("NonAsciiCharacters")
public class RestaurantFixture {

    public static Restaurant 교촌치킨() {
        return Restaurant.builder()
                .imageUrl("https://교촌치킨.com")
                .name("교촌치킨")
                .category(Category.ETC)
                .roadAddress("서울특별시 도봉구 도봉로 123길 45")
                .addressLotNumber("서울특별시 도봉구 방학동 12 - 1")
                .zipCode("14653")
                .phoneNumber("010-1234-5678")
                .latitude("126.9103408")
                .longitude("37.6055959")
                .build();
    }

    public static Restaurant 맥도날드() {
        return Restaurant.builder()
                .imageUrl("https://맥도날드.com")
                .name("맥도날드")
                .category(Category.ETC)
                .roadAddress("서울특별시 중구 서소문로 89-20")
                .addressLotNumber("서울특별시 중구 정동 37")
                .zipCode("04516")
                .phoneNumber("010-1234-5328")
                .latitude("197446.325172007")
                .longitude("451215.406399929")
                .build();
    }
}
