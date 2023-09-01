package com.celuveat.restaurant.query.dto;

import com.celuveat.celeb.command.domain.Celeb;

public record CelebQueryResponse(
        Long id,
        String name,
        String youtubeChannelName,
        String profileImageUrl
) {

    public static CelebQueryResponse of(Celeb it) {
        return new CelebQueryResponse(
                it.id(),
                it.name(),
                it.youtubeChannelName(),
                it.profileImageUrl()
        );
    }
}
