package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search;

public record Item(
        String kind,
        String etag,
        Id id,
        Snippet snippet
) {
}
