package com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.search;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public record Item(
        String kind,
        String etag,
        Id id,
        Snippet snippet
) {

    public LocalDateTime publishedAt() {
        String publishedAt = snippet.publishedAt();
        return LocalDateTime.from(
                Instant.from(
                        DateTimeFormatter.ISO_DATE_TIME.parse(publishedAt)
                ).atZone(ZoneId.of("Asia/Seoul"))
        );
    }
}
