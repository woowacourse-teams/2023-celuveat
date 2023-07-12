package com.celuveat.celuveat.video.domain;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record VideoHistory(
        Long id,
        Long celebId,
        String title,
        String videoId,
        int viewCount,
        LocalDateTime uploadDate,
        boolean ads
) {
}
