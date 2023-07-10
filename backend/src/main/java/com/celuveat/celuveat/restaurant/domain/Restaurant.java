package com.celuveat.celuveat.restaurant.domain;

import lombok.Builder;

@Builder
public record Restaurant(
        Long id,
        String imageUrl,
        String name,
        String roadAddress,
        String addressLotNumber,
        Category category,
        String phoneNumber,
        String latitude,
        String longitude,
        String zipCode
) {
}
