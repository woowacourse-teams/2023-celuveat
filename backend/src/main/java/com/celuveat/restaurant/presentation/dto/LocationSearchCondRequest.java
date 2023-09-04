package com.celuveat.restaurant.presentation.dto;

import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.LocationSearchCond;
import jakarta.validation.constraints.NotNull;

public record LocationSearchCondRequest(
        @NotNull Double lowLatitude,
        @NotNull Double highLatitude,
        @NotNull Double lowLongitude,
        @NotNull Double highLongitude
) {

    public LocationSearchCond toCondition() {
        return new LocationSearchCond(
                lowLatitude,
                highLatitude,
                lowLongitude,
                highLongitude
        );
    }
}
