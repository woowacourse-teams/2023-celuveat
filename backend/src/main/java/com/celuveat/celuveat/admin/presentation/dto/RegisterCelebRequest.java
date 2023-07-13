package com.celuveat.celuveat.admin.presentation.dto;

import com.celuveat.celuveat.celeb.application.dto.RegisterCelebCommand;

public record RegisterCelebRequest(
        String name,
        String youtubeChannelId,
        String youtubeChannelName,
        int subscriberCount,
        String youtubeChannelUrl,
        String profileImageUrl
) {

    public RegisterCelebCommand toCommand() {
        return RegisterCelebCommand.builder()
                .name(name)
                .youtubeChannelId(youtubeChannelId)
                .youtubeChannelName(youtubeChannelName)
                .subscriberCount(subscriberCount)
                .youtubeChannelUrl(youtubeChannelUrl)
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
