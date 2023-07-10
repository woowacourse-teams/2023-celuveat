package com.celuveat.celuveat.video.domain;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record Video(
        Long id,
        Long celebId,
        Long restaurantId,
        String title,
        String url,
        String thumbnailUrl,
        int viewCount,
        LocalDateTime uploadDate
) {
}
