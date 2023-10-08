package com.celuveat.celeb.query.dto;

import com.celuveat.celeb.command.domain.Celeb;

public record CelebQueryResponse(
        Long id,
        String name,
        String youtubeChannelName,
        String profileImageUrl
) {

    public static CelebQueryResponse from(Celeb celeb) {
        return new CelebQueryResponse(
                celeb.id(),
                celeb.name(),
                celeb.youtubeChannelName(),
                celeb.profileImageUrl()
        );
    }
}
