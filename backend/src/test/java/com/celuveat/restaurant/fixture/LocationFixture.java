package com.celuveat.restaurant.fixture;


import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;

public class LocationFixture {

    public static final Point 지점1 = new Point(37.515, 127.075);
    public static final Point 지점2 = new Point(37.525, 127.085);

    public static final LocationSearchCondRequest 박스_1_2번_지점포함_요청 =
            new LocationSearchCondRequest(37.50, 37.53, 127.06, 127.09);

    public record Point(
            Double latitude,
            Double longitude
    ) {
    }
}
