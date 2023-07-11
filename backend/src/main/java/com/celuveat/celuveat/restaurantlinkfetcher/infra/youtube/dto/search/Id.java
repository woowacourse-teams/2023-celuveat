package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search;

public record Id(
        String kind,
        String videoId,
        String channelId
) {
}
