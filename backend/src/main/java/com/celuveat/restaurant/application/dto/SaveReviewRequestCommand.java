package com.celuveat.restaurant.application.dto;

public record SaveReviewRequestCommand(
        String content,
        Long memberId,
        Long restaurantId
) {
}
