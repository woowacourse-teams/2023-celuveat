package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.video;

public record Item(
        String kind,
        String etag,
        String id,
        Snippet snippet,
        Statistics statistics
) {
}
