package com.celuveat.restaurant.domain.dto;

public record RestaurantIdWithLikeCount(
        Long restaurantId,
        Long count
) {
}
