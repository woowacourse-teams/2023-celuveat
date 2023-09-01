package com.celuveat.restaurant.presentation.dto;

import com.celuveat.restaurant.command.application.dto.SuggestCorrectionRequestCommand;
import java.util.List;

public record SuggestCorrectionRequest(
        List<String> contents
) {

    public SuggestCorrectionRequestCommand toCommand(Long restaurantId) {
        List<String> stripped = contents.stream()
                .map(String::strip)
                .toList();
        return new SuggestCorrectionRequestCommand(restaurantId, stripped);
    }
}
