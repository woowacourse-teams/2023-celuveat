package com.celuveat.restaurant.command.application.dto;

import java.util.List;

public record SuggestImagesCommand(
        Long restaurantId,
        Long memberId,
        List<String> imageNames
) {
}
