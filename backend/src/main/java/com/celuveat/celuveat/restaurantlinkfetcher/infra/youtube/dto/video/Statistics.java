package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.video;

public record Statistics(
        String viewCount,
        String likeCount,
        String favoriteCount,
        String commentCount
) {
}
