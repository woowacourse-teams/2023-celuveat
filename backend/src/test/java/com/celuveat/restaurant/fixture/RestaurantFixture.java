package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.command.domain.Restaurant;

public class RestaurantFixture {

    public static Restaurant 하늘초밥() {
        return Restaurant.builder()
                .name("하늘초밥")
                .category("초밥,롤")
                .superCategory("일식")
                .roadAddress("서울 서대문구 이화여대2길 14 1층 하늘초밥")
                .latitude(37.5572713)
                .longitude(126.9466788)
                .phoneNumber("0507-1366-4573")
                .naverMapUrl("https://map.naver.com/v5/entry/place/1960457705?entry=plt&c=15,0,0,0,dh")
                .build();
    }

    public static Restaurant 모던샤브하우스() {
        return Restaurant.builder()
                .name("모던샤브하우스")
                .category("샤브샤브")
                .superCategory("일식")
                .roadAddress("서울 종로구 종로3길 17 D타워 리플레이스 광화문 5층")
                .latitude(37.5710694)
                .longitude(126.978929)
                .phoneNumber("02-2251-8501")
                .naverMapUrl("https://map.naver.com/v5/entry/place/1838141891?entry=plt&c=15,0,0,0,dh")
                .build();
    }

    public static Restaurant 대성집() {
        return Restaurant.builder()
                .name("대성집")
                .category("곰탕,설렁탕")
                .superCategory("국, 국밥")
                .roadAddress("서울 종로구 행촌동 209-35")
                .latitude(37.5727172)
                .longitude(126.9609577)
                .phoneNumber("02-735-4259")
                .naverMapUrl("https://map.naver.com/v5/entry/place/13517178?c=15,0,0,0,dh")
                .build();
    }

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
                .build();
    }
}
