package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search;

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
}
