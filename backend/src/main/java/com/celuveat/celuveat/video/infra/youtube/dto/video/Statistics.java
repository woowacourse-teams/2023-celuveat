package com.celuveat.celuveat.video.infra.youtube.dto.video;

public record Statistics(
        String viewCount,
        String likeCount,
        String favoriteCount,
        String commentCount
) {
}
