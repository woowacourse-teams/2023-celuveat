package com.celuveat.acceptance.video;

import static com.celuveat.acceptance.common.AcceptanceSteps.없음;
import static com.celuveat.acceptance.video.VideoAcceptanceSteps.영상_응답_결과를_검증한다;
import static com.celuveat.acceptance.video.VideoAcceptanceSteps.영상_조회_요청;
import static com.celuveat.acceptance.video.VideoAcceptanceSteps.영상_조회_요청_데이터;
import static com.celuveat.acceptance.video.VideoAcceptanceSteps.영상_조회_응답;
import static com.celuveat.celeb.fixture.CelebFixture.맛객리우;
import static com.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celeb.fixture.CelebFixture.회사랑;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static com.celuveat.restaurant.fixture.RestaurantFixture.모던샤브하우스;
import static com.celuveat.restaurant.fixture.RestaurantFixture.하늘초밥;
import static com.celuveat.video.fixture.VideoFixture.맛객리우의_모던샤브하우스_영상;
import static com.celuveat.video.fixture.VideoFixture.성시경의_대성집_영상;
import static com.celuveat.video.fixture.VideoFixture.회사랑의_하늘초밥_영상;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.video.command.domain.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("영상 인수테스트")
public class VideoAcceptanceTest extends AcceptanceTest {

    @Nested
    class 영상_조회_API {

        private final Restaurant 대성집 = 대성집();
        private final Restaurant 하늘초밥 = 하늘초밥();
        private final Restaurant 모던샤브하우스 = 모던샤브하우스();
        private final Celeb 성시경 = 성시경();
        private final Celeb 회사랑 = 회사랑();
        private final Celeb 맛객리우 = 맛객리우();
        private final Video 성시경의_대성집_영상 = 성시경의_대성집_영상(성시경, 대성집);
        private final Video 회사랑의_하늘초밥_영상 = 회사랑의_하늘초밥_영상(회사랑, 하늘초밥);
        private final Video 맛객리우의_모던샤브하우스_영상 = 맛객리우의_모던샤브하우스_영상(맛객리우, 모던샤브하우스);

        @BeforeEach
        void setUp() {
            testData.addCelebs(성시경, 회사랑, 맛객리우);
            testData.addRestaurants(대성집, 하늘초밥, 모던샤브하우스);
            testData.addVideos(
                    성시경의_대성집_영상,
                    회사랑의_하늘초밥_영상,
                    맛객리우의_모던샤브하우스_영상
            );
            초기_데이터_저장();
        }

        @Test
        void 영상을_전체_조회한다() {
            // when
            var 응답 = 영상_조회_요청(영상_조회_요청_데이터(없음, 없음));

            // then
            var 예상_응답 = 영상_조회_응답(
                    성시경의_대성집_영상,
                    회사랑의_하늘초밥_영상,
                    맛객리우의_모던샤브하우스_영상
            );
            영상_응답_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 음식점ID로_영상을_조회한다() {
            // when
            var 응답 = 영상_조회_요청(영상_조회_요청_데이터(없음, 하늘초밥.id()));

            // then
            var 예상_응답 = 영상_조회_응답(회사랑의_하늘초밥_영상);
            영상_응답_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 셀럽ID로_영상을_조회한다() {
            // when
            var 응답 = 영상_조회_요청(영상_조회_요청_데이터(회사랑.id(), 없음));

            // then
            var 예상_응답 = 영상_조회_응답(회사랑의_하늘초밥_영상);
            영상_응답_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 음식점ID와_셀럽ID로_영상을_조회한다() {
            // when
            var 응답 = 영상_조회_요청(영상_조회_요청_데이터(회사랑.id(), 하늘초밥.id()));

            // then
            var 예상_응답 = 영상_조회_응답(회사랑의_하늘초밥_영상);
            영상_응답_결과를_검증한다(예상_응답, 응답);
        }
    }
}
