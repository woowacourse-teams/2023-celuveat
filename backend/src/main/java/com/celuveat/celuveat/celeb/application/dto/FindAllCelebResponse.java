package com.celuveat.celuveat.celeb.application.dto;

public record FindAllCelebResponse(
        Long id,
        String name,
        String youtubeId,
        int subscriberCount,
        String profileImageUrl
) {
}
