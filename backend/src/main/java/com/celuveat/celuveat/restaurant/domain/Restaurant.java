package com.celuveat.celuveat.restaurant.domain;

import lombok.Builder;

@Builder
public record Restaurant(
        Long id,
        String imageUrl,
        String name,
        String address,
        Category category,
        String rating,
        String phoneNumber
) {
}
