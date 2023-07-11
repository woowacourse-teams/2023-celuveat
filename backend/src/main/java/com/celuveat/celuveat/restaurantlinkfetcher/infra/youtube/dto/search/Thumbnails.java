package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Thumbnails(
        @JsonProperty("default")
        Thumbnail defaultThumbnail,
        @JsonProperty("medium")
        Thumbnail mediumThumbnail,
        @JsonProperty("high")
        Thumbnail highThumbnail
) {
}
