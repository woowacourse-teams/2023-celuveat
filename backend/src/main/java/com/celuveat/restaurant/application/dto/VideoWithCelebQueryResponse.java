package com.celuveat.restaurant.application.dto;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.video.domain.Video;
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
    public static VideoWithCelebQueryResponse from(Video video) {
        Celeb celeb = video.celeb();
        return new VideoWithCelebQueryResponse(
                video.id(),
                video.youtubeUrl(),
                video.uploadDate(),
                celeb.id(),
                celeb.name(),
                celeb.youtubeChannelName(),
                celeb.profileImageUrl()
        );
    }
}
