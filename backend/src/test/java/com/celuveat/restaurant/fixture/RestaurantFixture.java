package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantPoint;
import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.LocationSearchCond;
import java.util.Random;

public class RestaurantFixture {

    public static Boolean 좋아요_누름 = Boolean.TRUE;
    public static Boolean 좋아요_누르지_않음 = Boolean.FALSE;

    public static Restaurant 하늘초밥() {
        return Restaurant.builder()
                .name("하늘초밥")
                .category("초밥,롤")
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
                .roadAddress("서울 종로구 행촌동 209-35")
                .latitude(37.5727172)
                .longitude(126.9609577)
                .phoneNumber("02-735-4259")
                .naverMapUrl("https://map.naver.com/v5/entry/place/13517178?c=15,0,0,0,dh")
                .build();
    }

    public static Restaurant 음식점(String name, String category) {
        return 음식점(name, category, 37.5206993, 127.019975);
    }

    public static Restaurant 음식점(String name, String category, RestaurantPoint restaurantPoint) {
        return 음식점(name, category, restaurantPoint.latitude(), restaurantPoint.longitude());
    }

    public static Restaurant 음식점(String name, String category, double latitude, double longitude) {
        return Restaurant.builder()
                .name(name)
                .roadAddress(name)
                .naverMapUrl(name)
                .phoneNumber(name)
                .category(category)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public static class RestaurantPointFixture {

        public static LocationSearchCond 대성집만_포함한_검색_영역() {
            return 대성집만_포함한_검색_영역_요청().toCondition();
        }

        public static LocationSearchCond 하늘초밥만_포함한_검색_영역() {
            return 하늘초밥만_포함한_검색_영역_요청().toCondition();
        }

        public static LocationSearchCond 모던샤브하우스만_포함한_검색_영역() {
            return 모던샤브하우스만_포함한_검색_영역_요청().toCondition();
        }

        public static LocationSearchCond 대한민국_전체를_포함한_검색_영역() {
            return 대한민국_전체를_포함한_검색_영역_요청().toCondition();
        }

        public static LocationSearchCondRequest 대성집만_포함한_검색_영역_요청() {
            return new LocationSearchCondRequest(
                    37.570945043671536, 37.57528178613561, 126.9600811054725, 126.96204984688943
            );
        }

        public static LocationSearchCondRequest 하늘초밥만_포함한_검색_영역_요청() {
            return new LocationSearchCondRequest(
                    37.55513973710626, 37.55947739976768, 126.94575072929152, 126.94771947070845
            );
        }

        public static LocationSearchCondRequest 모던샤브하우스만_포함한_검색_영역_요청() {
            return new LocationSearchCondRequest(
                    37.56999357685741, 37.57216200735907, 126.97851187149867, 126.97949624220713
            );
        }

        public static LocationSearchCondRequest 대한민국_전체를_포함한_검색_영역_요청() {
            return new LocationSearchCondRequest(
                    31.555663574665545, 40.59357169075871, 125.66602297769718, 129.7199780558222
            );
        }

        public static RestaurantPoint 영역에_포함된_임의의_지점(
                double lowLatitude,
                double highLatitude,
                double lowLongitude,
                double highLongitude
        ) {
            Random rand = new Random();
            double randomLatitude = lowLatitude + (highLatitude - lowLatitude) * rand.nextDouble();
            double randomLongitude = lowLongitude + (highLongitude - lowLongitude) * rand.nextDouble();
            return new RestaurantPoint(randomLatitude, randomLongitude);
        }
    }
}
