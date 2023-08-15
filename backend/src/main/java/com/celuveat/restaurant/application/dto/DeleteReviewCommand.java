package com.celuveat.restaurant.application.dto;

public record DeleteReviewCommand(
        Long reviewId,
        Long memberId
) {
}
