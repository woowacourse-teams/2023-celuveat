package com.celuveat.video.query.dto;

import com.celuveat.video.utils.VideoResponseUtils;
import java.time.LocalDate;

public record VideoWithCelebQueryResponse(
        Long videoId,
        String youtubeVideoKey,
        LocalDate uploadDate,
        Long celebId,
        String name,
        String youtubeChannelName,
        String profileImageUrl
) {

    @Override
    public String youtubeVideoKey() {
        return VideoResponseUtils.extractVideoKey(youtubeVideoKey);
    }
}
