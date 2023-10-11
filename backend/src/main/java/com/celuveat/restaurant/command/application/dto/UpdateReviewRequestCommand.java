package com.celuveat.restaurant.command.application.dto;

public record UpdateReviewRequestCommand(
        String content,
        Long reviewId,
        Long memberId,
        double rating
) {
}
