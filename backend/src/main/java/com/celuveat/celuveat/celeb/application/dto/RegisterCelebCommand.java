package com.celuveat.celuveat.celeb.application.dto;

import com.celuveat.celuveat.celeb.domain.Celeb;
import lombok.Builder;

@Builder
public record RegisterCelebCommand(
        String name,
        String youtubeChannelId,
        String youtubeChannelName,
        int subscriberCount,
        String youtubeChannelUrl,
        String backgroundImageUrl,
        String profileImageUrl
) {

    public Celeb toDomain() {
        return Celeb.builder()
                .name(name)
                .youtubeChannelId(youtubeChannelId)
                .youtubeChannelName(youtubeChannelName)
                .subscriberCount(subscriberCount)
                .youtubeChannelUrl(youtubeChannelUrl)
                .backgroundImageUrl(backgroundImageUrl)
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
