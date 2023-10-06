package com.celuveat.celeb.query.dto;

import com.celuveat.celeb.command.domain.Celeb;

public record FindAllCelebResponse(
        Long id,
        String name,
        String youtubeChannelName,
        String profileImageUrl
) {

    public static FindAllCelebResponse from(Celeb celeb) {
        return new FindAllCelebResponse(
                celeb.id(),
                celeb.name(),
                celeb.youtubeChannelName(),
                celeb.profileImageUrl()
        );
    }
}
