package com.celuveat.restaurant.fixture;

public class PointFixture {

    public static final Point 기준점 = new Point(37.504487, 127.048957);
    public static final Point 기준점에서_2KM_지점 = new Point(37.504487, 127.0715651);
    public static final Point 기준점에서_3KM_지점 = new Point(37.504487, 127.0829151);
    public static final Point 기준점에서_5KM_지점 = new Point(37.504487, 127.105591);
    public static final Point 기준점에서_10KM_지점 = new Point(37.504487, 127.162271);

    public record Point(
            Double latitude,
            Double longitude
    ) {
    }
}
