package com.celuveat.celuveat.video.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record FindAllVideoHistoryResponse(
        Long id,
        Long celebId,
        String title,
        String youtubeVideoId,
        int viewCount,
        LocalDateTime uploadDate,
        @JsonProperty("isAds")
        boolean ads
) {
}
