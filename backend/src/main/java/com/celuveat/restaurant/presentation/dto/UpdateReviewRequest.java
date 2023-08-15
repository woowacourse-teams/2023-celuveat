package com.celuveat.restaurant.presentation.dto;

import com.celuveat.restaurant.application.dto.UpdateReviewRequestCommand;

public record UpdateReviewRequest(
        String content
) {

    public UpdateReviewRequestCommand toCommand(Long reviewId, Long memberId) {
        return new UpdateReviewRequestCommand(content, reviewId, memberId);
    }
}
