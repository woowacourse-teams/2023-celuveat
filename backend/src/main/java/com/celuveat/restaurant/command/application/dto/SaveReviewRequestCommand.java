package com.celuveat.restaurant.command.application.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record SaveReviewRequestCommand(
        String content,
        Long memberId,
        Long restaurantId,
        Double rating,
        List<MultipartFile> images
) {

    public SaveReviewRequestCommand(
            String content,
            Long memberId,
            Long restaurantId,
            Double rating
    ) {
        this(content, memberId, restaurantId, rating, List.of());
    }
}
