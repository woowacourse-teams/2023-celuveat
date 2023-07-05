package com.celuveat.celuveat.video.fixture;

import com.celuveat.celuveat.video.Video;
import java.time.LocalDateTime;

public class VideoFixture {

    public static Video 영상(Long celebId, Long restaurantId) {
        return Video.builder()
                .celebId(celebId)
                .restaurantId(restaurantId)
                .title("맛있는 음식점 다녀옴. 말랑 잘하네")
                .viewCount(3)
                .videoUrl("https://naver.com")
                .publishedDate(LocalDateTime.of(2000, 10, 4, 10, 21, 22))
                .build();
    }
}
