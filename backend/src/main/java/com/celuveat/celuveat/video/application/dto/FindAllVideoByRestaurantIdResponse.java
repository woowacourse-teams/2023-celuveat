package com.celuveat.celuveat.video.application.dto;

import java.time.LocalDateTime;

public record FindAllVideoByRestaurantIdResponse(
        Long id,
        String title,
        Long celebId,
        String celebName,
        int viewCount,
        String videoId,
        LocalDateTime uploadDate,
        String profileImageUrl
) {
}
