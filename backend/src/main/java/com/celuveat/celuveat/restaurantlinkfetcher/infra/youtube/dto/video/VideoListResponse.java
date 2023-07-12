package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.video;

import java.util.List;

public record VideoListResponse(
        String kind,
        String etag,
        List<Item> items,
        PageInfo pageInfo
) {
}
