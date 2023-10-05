package com.celuveat.acceptance.video;

import static com.celuveat.acceptance.common.AcceptanceSteps.없음;
import static com.celuveat.acceptance.video.VideoAcceptanceSteps.영상_응답_결과를_검증한다;
import static com.celuveat.acceptance.video.VideoAcceptanceSteps.영상_조회_예상_응답;
import static com.celuveat.acceptance.video.VideoAcceptanceSteps.영상_조회_요청;
import static com.celuveat.acceptance.video.VideoAcceptanceSteps.특정_셀럽의_영상을_추출한다;
import static com.celuveat.acceptance.video.VideoAcceptanceSteps.특정_음식점의_영상을_추출한다;
import static com.celuveat.acceptance.video.VideoAcceptanceSteps.특정_이름의_셀럽을_찾는다;
import static com.celuveat.acceptance.video.VideoAcceptanceSteps.특정_이름의_음식점을_찾는다;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.presentation.dto.VideoSearchCondRequest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("영상 인수 테스트")
public class VideoAcceptanceTest extends AcceptanceTest {

    @Nested
    class 영상_조회 {

        private List<Video> 전체_영상;

        @BeforeEach
        void setUp() {
            전체_영상 = seedData.insertVideoSeedData();
        }

        @Test
        void 영상을_전체_조회한다() {
            // when
            var 응답 = 영상_조회_요청(new VideoSearchCondRequest((Long) 없음, (Long) 없음));

            // then
            var 예상_응답 = 영상_조회_예상_응답(전체_영상);
            영상_응답_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 음식점ID로_영상을_조회한다() {
            // given
            var 로이스1호점 = 특정_이름의_음식점을_찾는다(전체_영상, "로이스1호점");
            var 로이스1호점_영상들 = 특정_음식점의_영상을_추출한다(전체_영상, 로이스1호점);

            // when
            var 응답 = 영상_조회_요청(new VideoSearchCondRequest((Long) 없음, 로이스1호점.id()));

            // then
            var 예상_응답 = 영상_조회_예상_응답(로이스1호점_영상들);
            영상_응답_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 셀럽ID로_영상을_조회한다() {
            // given
            var 로이스 = 특정_이름의_셀럽을_찾는다(전체_영상, "로이스");
            var 영상들 = 특정_셀럽의_영상을_추출한다(전체_영상, 로이스);

            // when
            var 응답 = 영상_조회_요청(new VideoSearchCondRequest(로이스.id(), (Long) 없음));

            // then
            var 예상_응답 = 영상_조회_예상_응답(영상들);
            영상_응답_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 음식점ID와_셀럽ID로_영상을_조회한다() {
            // given
            var 로이스1호점 = 특정_이름의_음식점을_찾는다(전체_영상, "로이스1호점");
            var 영상들 = 특정_음식점의_영상을_추출한다(전체_영상, 로이스1호점);
            var 로이스 = 특정_이름의_셀럽을_찾는다(영상들, "로이스");
            var 예상_응답 = 영상_조회_예상_응답(특정_셀럽의_영상을_추출한다(영상들, 로이스));

            // when
            var 응답 = 영상_조회_요청(new VideoSearchCondRequest(로이스.id(), 로이스1호점.id()));

            // then
            영상_응답_결과를_검증한다(예상_응답, 응답);
        }
    }
}
