package com.celuveat.celuveat.celeb.domain;

import lombok.Builder;

@Builder
public record Celeb(
        Long id,
        String name,
        String youtubeChannelId,
        String youtubeChannelName,
        int subscriberCount,
        String youtubeChannelUrl,
        String profileImageUrl
) {
}
