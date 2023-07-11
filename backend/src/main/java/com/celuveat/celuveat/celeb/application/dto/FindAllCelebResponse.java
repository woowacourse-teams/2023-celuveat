package com.celuveat.celuveat.celeb.application.dto;

public record FindAllCelebResponse(
        Long id,
        String name,
        String youtubeChannelName,
        int subscriberCount,
        String profileImageUrl
) {
}
