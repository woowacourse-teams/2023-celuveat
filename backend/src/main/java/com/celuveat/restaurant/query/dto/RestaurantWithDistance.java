package com.celuveat.restaurant.query.dto;

public record RestaurantWithDistance(
        Long id,
        String name,
        String category,
        String roadAddress,
        Double latitude,
        Double longitude,
        String phoneNumber,
        String naverMapUrl,
        Integer viewCount,
        Double distance,
        Long likeCount
) {
}
