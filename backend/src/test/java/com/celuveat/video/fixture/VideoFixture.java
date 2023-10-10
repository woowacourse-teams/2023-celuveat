package com.celuveat.video.fixture;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.video.command.domain.Video;
import java.time.LocalDate;

public class VideoFixture {

    public static Video 회사랑의_하늘초밥_영상(Celeb 회사랑, Restaurant 하늘초밥) {
        return Video.builder()
                .youtubeUrl("https://www.youtube.com/watch?v=GeZRxKUCFtM")
                .uploadDate(LocalDate.of(2021, 9, 21))
                .celeb(회사랑)
                .restaurant(하늘초밥)
                .build();
    }

    public static Video 맛객리우의_모던샤브하우스_영상(Celeb 맛객리우, Restaurant 모던샤브하우스) {
        return Video.builder()
                .youtubeUrl("https://www.youtube.com/watch?v=IjEE2N2FXxI")
                .uploadDate(LocalDate.of(2023, 2, 13))
                .celeb(맛객리우)
                .restaurant(모던샤브하우스)
                .build();
    }

    public static Video 성시경의_대성집_영상(Celeb 성시경, Restaurant 대성집) {
        return Video.builder()
                .youtubeUrl("https://www.youtube.com/watch?v=wu1fOmsPEr8")
                .uploadDate(LocalDate.of(2023, 3, 11))
                .celeb(성시경)
                .restaurant(대성집)
                .build();
    }

    public static Video 영상(String 영상_URL, Celeb 셀럽, Restaurant 음식점) {
        return Video.builder()
                .celeb(셀럽)
                .restaurant(음식점)
                .uploadDate(LocalDate.now())
                .youtubeUrl(영상_URL)
                .build();
    }

    public static Video 영상(Celeb 셀럽, Restaurant 음식점) {
        return 영상("https://" + 음식점.name(), 셀럽, 음식점);
    }
}
