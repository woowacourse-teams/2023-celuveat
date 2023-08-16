package com.celuveat.restaurant.application.dto;

public record UpdateReviewRequestCommand(
        String content,
        Long reviewId,
        Long memberId
) {
}
