package com.celuveat.video.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


@DisplayName("영상 관련 유틸리티(VideoResponseUtils) 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class VideoResponseUtilTest {

    @ParameterizedTest
    @CsvSource(value = {
            "https://www.youtube.com/watch?v=YfEY2fPIw8s&list=PLuMuHAJh9g_Py_PSm8gmHdlcil6CQ9QCM&index=76 --> YfEY2fPIw8s",
            "https://www.youtube.com/watch?v=yrxOA0RwmKg --> yrxOA0RwmKg",
            "https://www.youtube.com/watch?v=yS5SWiwsU6U --> yS5SWiwsU6U",
            "https://www.youtube.com/watch?v=yYcz4HTtjJU --> yYcz4HTtjJU",
            "https://www.youtube.com/watch?v=z3LLrRL1o18 --> z3LLrRL1o18",
            "https://www.youtube.com/watch?v=z7yxl67xe0M --> z7yxl67xe0M",
            "https://www.youtube.com/watch?v=z8qSs69MT3g --> z8qSs69MT3g",
            "https://www.youtube.com/watch?v=zQVvYfG4ETs&t=39 --> zQVvYfG4ETs",
            "https://www.youtube.com/shorts/1_v5kOOJ6xo?t=48 --> 1_v5kOOJ6xo",
            "https://www.youtube.com/shorts/AqZESPTfnns --> AqZESPTfnns",
            "https://www.youtube.com/shorts/GFDjAALa7BE?t=41 --> GFDjAALa7BE",
            "https://www.youtube.com/shorts/oFjx1P7ryeY --> oFjx1P7ryeY"
    }, delimiterString = " --> ")
    void 영상_키_값을_추출_테스트(String videoUrl, String expected) {
        // when
        String result = VideoResponseUtil.extractVideoKey(videoUrl);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
