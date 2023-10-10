package com.celuveat.restaurant.presentation.dto;

import com.celuveat.common.util.FileNameUtil;
import com.celuveat.restaurant.command.application.dto.SaveReviewRequestCommand;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.web.multipart.MultipartFile;

public record SaveReviewRequest(
        String content,
        Long restaurantId,
        double rating,
        List<MultipartFile> images
) {

    public SaveReviewRequestCommand toCommand(Long memberId) {
        if (images == null) {
            return new SaveReviewRequestCommand(content, memberId, restaurantId, rating, Collections.emptyList());
        }
        List<String> imageNames = images.stream()
                .map(MultipartFile::getOriginalFilename)
                .filter(Objects::nonNull)
                .map(FileNameUtil::removeExtension)
                .toList();
        return new SaveReviewRequestCommand(content, memberId, restaurantId, rating, imageNames);
    }
}
