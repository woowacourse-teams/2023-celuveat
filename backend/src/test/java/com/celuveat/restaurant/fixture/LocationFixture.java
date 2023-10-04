package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;
import com.celuveat.restaurant.query.dao.RestaurantSimpleResponseDao.LocationSearchCond;
import com.celuveat.restaurant.query.dto.RestaurantSimpleResponse;

public class LocationFixture {

    public static final Point 지점1 = new Point(37.515, 127.075);
    public static final Point 지점2 = new Point(37.525, 127.085);

    public static final LocationSearchCond 박스_1번_지점포함 =
            new LocationSearchCond(37.50, 37.52, 127.06, 127.08);
    public static final LocationSearchCond 박스_1_2번_지점포함 =
            new LocationSearchCond(37.50, 37.53, 127.06, 127.09);
    public static final LocationSearchCond 전체영역_검색_범위 =
            new LocationSearchCond(10.0, 70.0, 0.0, 170.0);

    public static final LocationSearchCondRequest 박스_1번_지점포함_요청 =
            new LocationSearchCondRequest(37.50, 37.52, 127.06, 127.08);
    public static final LocationSearchCondRequest 박스_1_2번_지점포함_요청 =
            new LocationSearchCondRequest(37.50, 37.53, 127.06, 127.09);
    public static final LocationSearchCondRequest 전체영역_검색_범위_요청 =
            new LocationSearchCondRequest(10.0, 70.0, 0.0, 170.0);

    private static final double KOREAN_LOW_LATITUDE = 32.47152281030587;
    private static final double KOREAN_HIGH_LATITUDE = 39.566516733752664;
    private static final double KOREAN_LOW_LONGITUDE = 125.60143271619597;
    private static final double KOREAN_HIGH_LONGITUDE = 130.16625205213347;

    public static final LocationSearchCond 대한민국_검색_조건 = new LocationSearchCond(
            KOREAN_LOW_LATITUDE,
            KOREAN_HIGH_LATITUDE,
            KOREAN_LOW_LONGITUDE,
            KOREAN_HIGH_LONGITUDE
    );

    public static boolean isRestaurantInArea(
            LocationSearchCond locationSearchCond,
            RestaurantSimpleResponse restaurantWithCelebsAndImagesSimpleResponse
    ) {
        return locationSearchCond.lowLatitude() <= restaurantWithCelebsAndImagesSimpleResponse.latitude()
                && restaurantWithCelebsAndImagesSimpleResponse.latitude() <= locationSearchCond.highLatitude()
                && locationSearchCond.lowLongitude() <= restaurantWithCelebsAndImagesSimpleResponse.longitude()
                && restaurantWithCelebsAndImagesSimpleResponse.longitude() <= locationSearchCond.highLongitude();
    }

    public record Point(
            Double latitude,
            Double longitude
    ) {
    }
}
