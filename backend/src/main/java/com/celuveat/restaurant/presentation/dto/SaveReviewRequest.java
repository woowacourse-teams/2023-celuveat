package com.celuveat.restaurant.presentation.dto;

import com.celuveat.restaurant.command.application.dto.SaveReviewRequestCommand;

public record SaveReviewRequest(
        String content,
        Long restaurantId
) {

    public SaveReviewRequestCommand toCommand(Long memberId) {
        return new SaveReviewRequestCommand(content, memberId, restaurantId);
    }
}
