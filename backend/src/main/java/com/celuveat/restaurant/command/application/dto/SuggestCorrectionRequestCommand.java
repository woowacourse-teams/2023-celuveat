package com.celuveat.restaurant.command.application.dto;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.correction.RestaurantCorrection;
import java.util.List;
import lombok.Builder;

@Builder
public record SuggestCorrectionRequestCommand(
        Long restaurantId,
        List<String> contents
) {

    public List<RestaurantCorrection> toDomains(Restaurant restaurant) {
        return contents.stream()
                .map(it -> new RestaurantCorrection(it, restaurant))
                .toList();
    }
}
