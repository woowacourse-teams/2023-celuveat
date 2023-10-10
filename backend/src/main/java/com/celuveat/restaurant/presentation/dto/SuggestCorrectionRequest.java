package com.celuveat.restaurant.presentation.dto;

import com.celuveat.restaurant.command.application.dto.SuggestCorrectionCommand;
import java.util.List;

public record SuggestCorrectionRequest(
        List<String> contents
) {

    public SuggestCorrectionCommand toCommand(Long restaurantId) {
        List<String> stripped = contents.stream()
                .map(String::strip)
                .toList();
        return new SuggestCorrectionCommand(restaurantId, stripped);
    }
}
