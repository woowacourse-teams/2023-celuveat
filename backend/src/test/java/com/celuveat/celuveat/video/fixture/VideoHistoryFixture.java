package com.celuveat.celuveat.video.fixture;

import com.celuveat.celuveat.video.domain.VideoHistory;
import java.time.LocalDateTime;

@SuppressWarnings("NonAsciiCharacters")
public class VideoHistoryFixture {

    public static VideoHistory 영상_이력(Long 셀럽_ID) {
        return VideoHistory.builder()
                .celebId(셀럽_ID)
                .title("맛있는 음식점 다녀옴. 말랑 잘하네")
                .viewCount(3)
                .videoId("8RdkFuFK1DY")
                .uploadDate(LocalDateTime.of(2000, 10, 4, 10, 21, 22))
                .build();
    }
}
