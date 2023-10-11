package com.celuveat.event.command.application.dto;

import com.celuveat.event.command.domain.EventImage;
import java.util.List;

public record SubmitCommand(
        String instagramId,
        String restaurantName,
        List<String> imageNames
) {
    public List<EventImage> toDomains() {
        return imageNames.stream()
                .map(it -> new EventImage(instagramId, restaurantName, it))
                .toList();
    }
}
