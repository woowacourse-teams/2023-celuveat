package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search;

import java.util.List;

public record SearchListResponse(
        String kind,
        String etag,
        String nextPageToken,
        String prevPageToken,
        String regionCode,
        PageInfo pageInfo,
        List<Item> items
) {
}
