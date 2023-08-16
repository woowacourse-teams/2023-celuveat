package com.celuveat.video.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class VideoResponseUtilsTest {

    @ParameterizedTest
    @CsvSource(value = {
            "https://www.youtube.com/watch?v=YfEY2fPIw8s&list=PLuMuHAJh9g_Py_PSm8gmHdlcil6CQ9QCM&index=76 --> YfEY2fPIw8s",
            "https://www.youtube.com/watch?v=yrxOA0RwmKg --> yrxOA0RwmKg",
            "https://www.youtube.com/watch?v=yS5SWiwsU6U --> yS5SWiwsU6U",
            "https://www.youtube.com/watch?v=yYcz4HTtjJU --> yYcz4HTtjJU",
            "https://www.youtube.com/watch?v=z3LLrRL1o18 --> z3LLrRL1o18",
            "https://www.youtube.com/watch?v=z7yxl67xe0M --> z7yxl67xe0M",
            "https://www.youtube.com/watch?v=z8qSs69MT3g --> z8qSs69MT3g",
            "https://www.youtube.com/watch?v=zQVvYfG4ETs&t=39 --> zQVvYfG4ETs"
    }, delimiterString = " --> ")
    void 영상_키_값을_추출_테스트(String videoUrl, String expectedVideoKey) {
        String extracted = VideoResponseUtils.extractVideoKey(videoUrl);

        assertThat(extracted).isEqualTo(expectedVideoKey);
    }
}
