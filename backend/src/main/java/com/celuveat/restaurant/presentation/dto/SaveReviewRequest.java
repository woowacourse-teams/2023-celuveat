package com.celuveat.restaurant.presentation.dto;

import com.celuveat.restaurant.command.application.dto.SaveReviewRequestCommand;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record SaveReviewRequest(
        String content,
        Long restaurantId,
        Double rating,
        List<MultipartFile> images
) {

    public SaveReviewRequestCommand toCommand(Long memberId) {
        return new SaveReviewRequestCommand(content, memberId, restaurantId, rating, images);
    }
}
