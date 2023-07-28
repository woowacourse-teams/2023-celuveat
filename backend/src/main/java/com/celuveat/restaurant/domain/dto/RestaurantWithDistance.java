package com.celuveat.restaurant.domain.dto;

public record RestaurantWithDistance(
        Long id,
        String name,
        String category,
        String roadAddress,
        Double latitude,
        Double longitude,
        String phoneNumber,
        String naverMapUrl,
        Double distance
) {
}
