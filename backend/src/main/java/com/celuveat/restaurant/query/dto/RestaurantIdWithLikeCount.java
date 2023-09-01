package com.celuveat.restaurant.query.dto;

public record RestaurantIdWithLikeCount(
        Long restaurantId,
        Long count
) {
}
