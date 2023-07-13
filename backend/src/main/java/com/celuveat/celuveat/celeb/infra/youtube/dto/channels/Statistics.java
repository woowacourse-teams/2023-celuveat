package com.celuveat.celuveat.celeb.infra.youtube.dto.channels;

public record Statistics(
        String viewCount,
        String subscriberCount,
        String hiddenSubscriberCount,
        String videoCount
) {
}
