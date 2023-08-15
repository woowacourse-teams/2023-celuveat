package com.celuveat.restaurant.presentation.dto;

import com.celuveat.restaurant.application.dto.SaveReviewRequestCommand;

public record SaveReviewRequest(
        String content
) {

    public SaveReviewRequestCommand toCommand(Long memberId, Long restaurantId) {
        return new SaveReviewRequestCommand(content, memberId, restaurantId);
    }
}
