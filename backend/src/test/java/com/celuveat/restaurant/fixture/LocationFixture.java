package com.celuveat.restaurant.fixture;


import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.LocationSearchCond;

public class LocationFixture {

    public static final Point 지점1 = new Point(37.515, 127.075);
    public static final Point 지점2 = new Point(37.525, 127.085);

    public static final LocationSearchCond 박스_1번_지점포함 = new LocationSearchCond(37.50, 37.52, 127.06, 127.08);
    public static final LocationSearchCond 박스_1_2번_지점포함 = new LocationSearchCond(37.50, 37.53, 127.06, 127.09);
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

    public static LocationSearchCond 검색_영역(Object 포함_영역) {
        if (포함_영역 == null) {
            return new LocationSearchCond(null, null, null, null);
        }
        LocationSearchCond 검색_영역 = (LocationSearchCond) 포함_영역;
        return new LocationSearchCond(
                검색_영역.lowLatitude(),
                검색_영역.highLatitude(),
                검색_영역.lowLongitude(),
                검색_영역.highLongitude()
        );
    }

    public static LocationSearchCondRequest 검색_영역_요청(Object 포함_영역) {
        if (포함_영역 == null) {
            return new LocationSearchCondRequest(null, null, null, null);
        }
        LocationSearchCondRequest 검색_영역 = (LocationSearchCondRequest) 포함_영역;
        return new LocationSearchCondRequest(
                검색_영역.lowLatitude(),
                검색_영역.highLatitude(),
                검색_영역.lowLongitude(),
                검색_영역.highLongitude()
        );
    }

    public record Point(
            Double latitude,
            Double longitude
    ) {
    }
}
