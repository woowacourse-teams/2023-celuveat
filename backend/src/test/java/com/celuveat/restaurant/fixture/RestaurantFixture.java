package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.query.dto.CelebQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchResponse;
import java.util.List;

public class RestaurantFixture {

    public static Restaurant 도슬박() {
        return Restaurant.builder()
                .name("도슬박")
                .category("한식")
                .phoneNumber("0507-1498-1171")
                .naverMapUrl("https://naver.me/5cTMvoQK")
                .roadAddress("서울 강남구 압구정로42길 25-3 1층")
                .latitude(37.5272713)
                .longitude(127.0354068)
                .build();
    }

    public static Restaurant 카이센동_우니도() {
        return Restaurant.builder()
                .name("카이센동 우니도")
                .category("일식당")
                .phoneNumber("0507-1401-0517")
                .naverMapUrl("https://naver.me/xlWVxI7t")
                .roadAddress("서울 강남구 압구정로2길 15 1층 카이센동우니도")
                .latitude(37.5206993)
                .longitude(127.019975)
                .build();
    }

    public static Restaurant 히츠지야_가로수길점() {
        return Restaurant.builder()
                .name("히츠지야 가로수길점")
                .category("양고기")
                .phoneNumber("0507-1322-8062")
                .naverMapUrl("https://naver.me/FOhxjwTP")
                .roadAddress("서울 강남구 압구정로2길 46 1층 19호")
                .latitude(37.5187712)
                .longitude(127.0206688)
                .build();
    }

    public static Restaurant 조우() {
        return Restaurant.builder()
                .name("조우")
                .category("소고기구이")
                .phoneNumber("0507-1381-5802")
                .naverMapUrl("https://naver.me/5vIYBs6i")
                .roadAddress("서울 강남구 논현로 831 1층")
                .latitude(37.5225787)
                .longitude(127.0276935)
                .build();
    }

    public static Restaurant 육길() {
        return Restaurant.builder()
                .name("육길")
                .category("육류,고기요리")
                .phoneNumber("0507-1323-3532")
                .naverMapUrl("https://naver.me/5D3wdNyX")
                .roadAddress("제주 서귀포시 월드컵로 107 . 1층")
                .latitude(33.2401517)
                .longitude(126.5057106)
                .build();
    }

    public static Restaurant 제주황돈() {
        return Restaurant.builder()
                .name("제주황돈")
                .category("돼지고기구이")
                .phoneNumber("0507-1352-7211")
                .naverMapUrl("https://naver.me/FeCLyog2")
                .roadAddress("제주 제주시 1100로 3001 1층")
                .latitude(33.4546606)
                .longitude(126.4865432)
                .build();
    }

    public static class 지역별_음식점 {

        public static List<Restaurant> 압구정_음식점들() {
            return List.of(도슬박(), 카이센동_우니도(), 히츠지야_가로수길점(), 조우());
        }

        public static List<Restaurant> 제주_음식점들() {
            return List.of(육길(), 제주황돈());
        }
    }

    public static final Restaurant 국민연금_구내식당 = Restaurant.builder()
            .name("국민연금")
            .roadAddress("국민연금 주소")
            .category("한식")
            .superCategory("한식")
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

    public static boolean isCelebVisited(
            Long celebId, RestaurantSearchResponse restaurantSearchResponse
    ) {
        List<Long> celebIds = restaurantSearchResponse.getCelebs()
                .stream()
                .map(CelebQueryResponse::id)
                .toList();
        return celebIds.contains(celebId);
    }
}
