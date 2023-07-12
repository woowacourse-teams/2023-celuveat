package com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.video;

public record Statistics(
        String viewCount,
        String likeCount,
        String favoriteCount,
        String commentCount
) {
}
