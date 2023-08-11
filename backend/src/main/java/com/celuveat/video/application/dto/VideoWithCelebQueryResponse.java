package com.celuveat.video.application.dto;

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

    public VideoWithCelebQueryResponse(
            Long videoId,
            String youtubeVideoKey,
            LocalDate uploadDate,
            Long celebId,
            String name,
            String youtubeChannelName,
            String profileImageUrl
    ) {
        this.videoId = videoId;
        this.youtubeVideoKey = VideoResponseUtils.extractVideoKey(youtubeVideoKey);
        this.uploadDate = uploadDate;
        this.celebId = celebId;
        this.name = name;
        this.youtubeChannelName = youtubeChannelName;
        this.profileImageUrl = profileImageUrl;
    }
}
