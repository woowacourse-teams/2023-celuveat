package com.celuveat.restaurant.application.dto;

import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.correction.RestaurantCorrection;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateRestaurantCorrectionCommand(
        Long restaurantId,
        List<String> contents
) {

    public List<RestaurantCorrection> toDomains(Restaurant restaurant) {
        return contents.stream()
                .map(it -> new RestaurantCorrection(it, restaurant))
                .toList();
    }
}
