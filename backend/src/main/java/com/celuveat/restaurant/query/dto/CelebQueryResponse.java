package com.celuveat.restaurant.query.dto;

import com.celuveat.celeb.command.domain.Celeb;

public record CelebQueryResponse(
        Long restaurantId,
        Long id,
        String name,
        String youtubeChannelName,
        String profileImageUrl
) {

    public static CelebQueryResponse from(Long restaurantId, Celeb celeb) {
        return new CelebQueryResponse(restaurantId,
                celeb.id(),
                celeb.name(),
                celeb.youtubeChannelName(),
                celeb.profileImageUrl()
        );
    }
}
