package com.celuveat.video.query.dto;

import com.celuveat.video.utils.VideoResponseUtil;
import java.time.LocalDate;

public record VideoQueryResponse(
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
        return VideoResponseUtil.extractVideoKey(youtubeVideoKey);
    }
}
