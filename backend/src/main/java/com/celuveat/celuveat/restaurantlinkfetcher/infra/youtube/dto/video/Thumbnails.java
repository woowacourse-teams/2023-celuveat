package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.video;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Thumbnails(
        @JsonProperty("default")
        Thumbnail defaultThumbnail,
        @JsonProperty("medium")
        Thumbnail mediumThumbnail,
        @JsonProperty("high")
        Thumbnail highThumbnail,
        @JsonProperty("standard")
        Thumbnail standardThumbnail
) {
}
