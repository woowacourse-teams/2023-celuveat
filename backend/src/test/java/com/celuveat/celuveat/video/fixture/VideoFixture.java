package com.celuveat.celuveat.video.fixture;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.video.application.dto.FindAllVideoByRestaurantIdResponse;
import com.celuveat.celuveat.video.domain.Video;
import java.time.LocalDateTime;

@SuppressWarnings("NonAsciiCharacters")
public class VideoFixture {

    public static Video 영상(Long 셀럽_ID, Long 음식점_ID) {
        return Video.builder()
                .celebId(셀럽_ID)
                .restaurantId(음식점_ID)
                .title("맛있는 음식점 다녀옴. 말랑 잘하네")
                .viewCount(3)
                .url("https://naver.com")
                .thumbnailUrl("https://썸네일_url.com")
                .uploadDate(LocalDateTime.now())
                .ads(false)
                .build();
    }

    public static FindAllVideoByRestaurantIdResponse toFindAllVideoByRestaurantIdResponse(Video video, Celeb celeb) {
        return new FindAllVideoByRestaurantIdResponse(
                video.id(),
                video.title(),
                video.celebId(),
                celeb.name(),
                video.viewCount(),
                video.url(),
                video.thumbnailUrl(),
                video.uploadDate(),
                celeb.profileImageUrl()
        );
    }
}
