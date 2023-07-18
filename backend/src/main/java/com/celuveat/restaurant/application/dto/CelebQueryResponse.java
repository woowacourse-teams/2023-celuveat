package com.celuveat.restaurant.application.dto;

import com.celuveat.celeb.domain.Celeb;

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
