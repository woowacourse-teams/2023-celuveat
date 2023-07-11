package com.celuveat.celuveat.restaurant.domain;

import lombok.Builder;

@Builder
public record Restaurant(
        Long id,
        String imageUrl,
        String name,
        Category category,
        String roadAddress,
        String addressLotNumber,
        String zipCode,
        String latitude,
        String longitude,
        String phoneNumber
) {
}
