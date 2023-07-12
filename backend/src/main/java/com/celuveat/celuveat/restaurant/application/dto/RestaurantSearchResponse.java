package com.celuveat.celuveat.restaurant.application.dto;

import com.celuveat.celuveat.restaurant.domain.Category;

public record RestaurantSearchResponse(
        Long id,
        String imageUrl,
        String name,
        Category category,
        String roadAddress,
        String addressLotNumber,
        String zipCode,
        String latitude,
        String longitude,
        String phoneNumber,
        boolean isAds
) {
}
