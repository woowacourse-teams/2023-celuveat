package com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.search;

public record Snippet(
        String publishedAt,
        String channelId,
        String title,
        String description,
        Thumbnails thumbnails,
        String channelTitle,
        String liveBroadcastContent,
        String publishTime
) {
}
