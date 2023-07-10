package com.celuveat.celuveat.video.application.dto;

import java.time.LocalDateTime;

public record FindAllVideoByRestaurantIdResponse(
        Long id,
        String title,
        Long celebId,
        String celebName,
        int viewCount,
        String videoUrl,
        String thumbnailUrl,
        LocalDateTime publishedDate,
        String profileImageUrl
) {
}
