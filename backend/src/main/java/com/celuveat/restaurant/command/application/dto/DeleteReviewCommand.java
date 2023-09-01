package com.celuveat.restaurant.command.application.dto;

public record DeleteReviewCommand(
        Long reviewId,
        Long memberId
) {
}
