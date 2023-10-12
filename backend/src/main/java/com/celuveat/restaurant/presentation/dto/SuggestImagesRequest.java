package com.celuveat.restaurant.presentation.dto;

import static com.celuveat.restaurant.exception.RestaurantImageSuggestionExceptionType.IMAGES_CAN_NOT_BE_NULL;

import com.celuveat.common.util.FileNameUtil;
import com.celuveat.restaurant.command.application.dto.SuggestImagesCommand;
import com.celuveat.restaurant.exception.RestaurantImageSuggestionException;
import java.util.List;
import java.util.Objects;
import org.springframework.web.multipart.MultipartFile;

public record SuggestImagesRequest(
        Long restaurantId,
        List<MultipartFile> images
) {

    public SuggestImagesCommand toCommand(Long memberId) {
        if (Objects.isNull(images)) {
            throw new RestaurantImageSuggestionException(IMAGES_CAN_NOT_BE_NULL);
        }
        List<String> imageNames = images.stream()
                .map(MultipartFile::getOriginalFilename)
                .filter(Objects::nonNull)
                .map(FileNameUtil::removeExtension)
                .toList();
        return new SuggestImagesCommand(restaurantId, memberId, imageNames);
    }
}