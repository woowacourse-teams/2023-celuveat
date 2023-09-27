package com.celuveat.restaurant.presentation.dto;

import com.celuveat.restaurant.command.application.dto.UpdateReviewRequestCommand;

public record UpdateReviewRequest(
        String content,
        Double rating
) {

    public UpdateReviewRequestCommand toCommand(Long reviewId, Long memberId) {
        return new UpdateReviewRequestCommand(content, reviewId, memberId, rating);
    }
}
