package com.celuveat.celuveat.celeb.infra.youtube.dto.channels;

public record Item(
        String kind,
        String etag,
        String id,
        Snippet snippet,
        Statistics statistics
) {

    public int subscriberCount() {
        return Integer.parseInt(statistics.subscriberCount());
    }

    public String profileImageUrl() {
        return snippet.thumbnails().highThumbnail().url();
    }
}
