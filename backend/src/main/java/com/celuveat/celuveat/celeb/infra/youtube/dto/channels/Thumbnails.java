package com.celuveat.celuveat.celeb.infra.youtube.dto.channels;

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
