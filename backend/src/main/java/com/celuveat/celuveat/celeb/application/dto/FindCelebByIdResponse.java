package com.celuveat.celuveat.celeb.application.dto;

public record FindCelebByIdResponse(
        Long id,
        String name,
        String youtubeChannelName,
        int subscriberCount,
        String youtubeChannelUrl,
        String backgroundImageUrl,
        String profileImageUrl,
        int restaurantCount
) {
}
