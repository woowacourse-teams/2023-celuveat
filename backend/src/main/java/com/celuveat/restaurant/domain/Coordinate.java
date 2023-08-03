package com.celuveat.restaurant.domain;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {

    private static final double EARTH_RADIUS = 6371;
    private static final double KILOMETER_TO_METER = 1000;

    private Double latitude;
    private Double longitude;

    public double calculateDistance(Coordinate other) {
        return EARTH_RADIUS * haversine(other) * KILOMETER_TO_METER;
    }

    private double haversine(Coordinate other) {
        double dLat = toRadians(other.latitude - latitude);
        double dLng = toRadians(other.longitude - longitude);
        double a = sin(dLat / 2) * sin(dLat / 2)
                + cos(toRadians(latitude))
                * cos(toRadians(other.latitude))
                * sin(dLng / 2) * sin(dLng / 2);
        return 2 * atan2(sqrt(a), sqrt(1 - a));
    }
}
