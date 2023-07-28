package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.LocationSearchCond;

public class LocationFixture {

    public static final Point 지점1 = new Point(37.515, 127.075);
    public static final Point 지점2 = new Point(37.525, 127.085);

    public static final LocationSearchCond 박스_1번_지점포함 = new LocationSearchCond(37.50, 37.52, 127.06, 127.08);
    public static final LocationSearchCond 박스_1_2번_지점포함 = new LocationSearchCond(37.50, 37.53, 127.06, 127.09);
    public static final LocationSearchCond 전체영역_검색_범위 =
            new LocationSearchCond(10.0, 70.0, 0.0, 170.0);

    public static boolean isRestaurantInArea(
            LocationSearchCond locationSearchCond,
            RestaurantQueryResponse restaurantQueryResponse
    ) {
        return locationSearchCond.lowLatitude() <= restaurantQueryResponse.latitude()
                && restaurantQueryResponse.latitude() <= locationSearchCond.highLatitude()
                && locationSearchCond.lowLongitude() <= restaurantQueryResponse.longitude()
                && restaurantQueryResponse.longitude() <= locationSearchCond.highLongitude();
    }

    public record Point(
            Double latitude,
            Double longitude
    ) {
    }
}
