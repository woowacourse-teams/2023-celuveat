package com.celuveat.celuveat.video.infra.youtube.dto.video;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public record Item(
        String kind,
        String etag,
        String id,
        Snippet snippet,
        Statistics statistics
) {

    public LocalDateTime publishedAt() {
        String publishedAt = snippet.publishedAt();
        return LocalDateTime.from(
                Instant.from(
                        DateTimeFormatter.ISO_DATE_TIME.parse(publishedAt)
                ).atZone(ZoneId.of("Asia/Seoul"))
        );
    }

    public int viewCount() {
        String viewCount = statistics.viewCount();
        return Integer.parseInt(viewCount);
    }
}
