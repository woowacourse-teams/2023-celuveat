package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record SearchListResponse(
        String kind,
        String etag,
        String nextPageToken,
        String prevPageToken,
        String regionCode,
        PageInfo pageInfo,
        List<Item> items
) {

    public List<String> videoIds() {
        return items.stream()
                .map(item -> item.id().videoId())
                .collect(Collectors.toList());
    }

    public List<String> afterVideoIds(LocalDateTime startDateTime) {
        return items.stream()
                .filter(item -> item.publishedAt().isAfter(startDateTime))
                .map(item -> item.id().videoId())
                .collect(Collectors.toList());
    }
}
