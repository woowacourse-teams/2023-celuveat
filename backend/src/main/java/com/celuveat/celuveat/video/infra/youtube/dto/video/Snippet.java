package com.celuveat.celuveat.video.infra.youtube.dto.video;

import java.util.List;

public record Snippet(
        String publishedAt,
        String channelId,
        String title,
        String description,
        Thumbnails thumbnails,
        String channelTitle,
        List<String> tags,
        String categoryId,
        String liveBroadcastContent,
        String defaultLanguage,
        Localized localized,
        String defaultAudioLanguage
) {
}
