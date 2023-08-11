package com.celuveat.video.application.dto;

import java.time.LocalDate;

public record VideoWithCelebQueryResponse(
        Long videoId,
        String youtubeUrl,
        LocalDate uploadDate,
        Long celebId,
        String name,
        String youtubeChannelName,
        String profileImageUrl
) {
}
