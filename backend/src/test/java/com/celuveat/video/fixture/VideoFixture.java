package com.celuveat.video.fixture;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.video.domain.Video;
import java.time.LocalDate;

public class VideoFixture {

    public static Video 영상(String name, Restaurant 음식점, Celeb 셀럽) {
        return Video.builder()
                .celeb(셀럽)
                .restaurant(음식점)
                .uploadDate(LocalDate.now())
                .youtubeUrl(name)
                .build();
    }
}
