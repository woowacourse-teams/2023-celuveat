package com.celuveat.restaurant.command.application.dto;

public record SaveReviewRequestCommand(
        String content,
        Long memberId,
        Long restaurantId
) {
}
