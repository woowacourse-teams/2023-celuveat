package com.celuveat.celeb.presentation.response;

import com.celuveat.celeb.domain.Celeb;

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
