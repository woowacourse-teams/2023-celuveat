package com.celuveat.restaurant.command.application.dto;

import java.util.Collections;
import java.util.List;

public record SaveReviewRequestCommand(
        String content,
        Long memberId,
        Long restaurantId,
        double rating,
        List<String> images
) {

    public SaveReviewRequestCommand(
            String content,
            Long memberId,
            Long restaurantId,
            double rating
    ) {
        this(content, memberId, restaurantId, rating, Collections.emptyList());
    }
}
