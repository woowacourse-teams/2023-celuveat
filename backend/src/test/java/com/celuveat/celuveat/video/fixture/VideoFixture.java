package com.celuveat.celuveat.video.fixture;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.video.application.dto.FindAllVideoByRestaurantIdResponse;
import com.celuveat.celuveat.video.domain.Video;
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

    public static FindAllVideoByRestaurantIdResponse toFindAllVideoByRestaurantIdResponse(Video video, Celeb celeb) {
        return new FindAllVideoByRestaurantIdResponse(
                video.id(),
                video.title(),
                video.celebId(),
                celeb.name(),
                video.viewCount(),
                video.videoUrl(),
                video.publishedDate(),
                celeb.profileImageUrl()
        );
    }
}
