package com.celuveat.celuveat.video.infra.youtube.dto.video;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.video.domain.VideoHistory;
import java.util.List;

public record VideoListResponse(
        String kind,
        String etag,
        List<Item> items,
        PageInfo pageInfo
) {

    public VideoHistory toVideoHistory(Celeb celeb) {
        Item item = items.get(0);
        return VideoHistory.builder()
                .celebId(celeb.id())
                .title(item.snippet().title())
                .youtubeVideoId(item.id())
                .viewCount(item.viewCount())
                .uploadDate(item.publishedAt())
                .ads(false)
                .build();
    }
}
