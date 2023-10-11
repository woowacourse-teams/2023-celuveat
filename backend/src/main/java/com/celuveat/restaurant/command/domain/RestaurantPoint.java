package com.celuveat.restaurant.command.domain;


import static lombok.AccessLevel.PROTECTED;
import static org.geolatte.geom.builder.DSL.g;
import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import org.geolatte.geom.Point;
import org.geolatte.geom.builder.DSL;


@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class RestaurantPoint {

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private Point<?> point;

    public RestaurantPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.point = DSL.point(WGS84, g(longitude, latitude));
    }

    public double latitude() {
        return latitude;
    }

    public double longitude() {
        return longitude;
    }

    public Point<?> point() {
        return point;
    }
}
