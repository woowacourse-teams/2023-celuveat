package com.celuveat.celuveat.celeb.infra.youtube.dto.channels;

public record Snippet(
        String title,
        String description,
        String customUrl,
        String publishedAt,
        Thumbnails thumbnails,
        String defaultLanguage,
        Localized localized,
        String country
) {
}
