package com.celuveat.celuveat.video.domain;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record Video(
        Long id,
        Long celebId,
        Long restaurantId,
        String title,
        String youtubeVideoId,
        int viewCount,
        LocalDateTime uploadDate,
        boolean ads
) {
}
