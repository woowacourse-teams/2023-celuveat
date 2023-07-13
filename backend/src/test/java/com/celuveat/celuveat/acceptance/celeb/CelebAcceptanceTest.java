package com.celuveat.celuveat.acceptance.celeb;

import static com.celuveat.celuveat.acceptance.celeb.CelebAcceptanceSteps.단일_셀럽_조회_결과를_검증한다;
import static com.celuveat.celuveat.acceptance.celeb.CelebAcceptanceSteps.단일_셀럽_조회_요청;
import static com.celuveat.celuveat.acceptance.celeb.CelebAcceptanceSteps.예상되는_단일_셀럽_조회_응답;
import static com.celuveat.celuveat.acceptance.celeb.CelebAcceptanceSteps.예상되는_전체_셀럽_조회_응답;
import static com.celuveat.celuveat.acceptance.celeb.CelebAcceptanceSteps.전체_셀럽_조회_결과를_검증한다;
import static com.celuveat.celuveat.acceptance.celeb.CelebAcceptanceSteps.전체_셀럽_조회_요청;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.정상_처리됨;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.교촌치킨;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.맥도날드;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.미스터피자;
import static com.celuveat.celuveat.video.fixture.VideoFixture.영상;

import com.celuveat.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Celeb 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
class CelebAcceptanceTest extends AcceptanceTest {

    @Test
    void 모든_셀럽을_조회한다() {
        // given
        var 성시경 = 성시경();
        var 히밥 = 히밥();
        셀럽을_저장한다(성시경);
        셀럽을_저장한다(히밥);
        var 예상_셀럽_조회_응답 = 예상되는_전체_셀럽_조회_응답(성시경, 히밥);

        // when
        var 응답 = 전체_셀럽_조회_요청();

        // then
        응답_상태를_검증한다(응답, 정상_처리됨);
        전체_셀럽_조회_결과를_검증한다(예상_셀럽_조회_응답, 응답);
    }

    @Test
    void 단일_셀럽을_조회한다() {
        // given
        var 히밥 = 히밥();
        var 성시경 = 성시경();
        var 히밥_ID = 셀럽을_저장한다(히밥);
        var 성시경_ID = 셀럽을_저장한다(성시경);

        var 맥도날드_ID = 음식점을_저장한다(맥도날드());
        var 교촌치킨_ID = 음식점을_저장한다(교촌치킨());
        var 미스터피자_ID = 음식점을_저장한다(미스터피자());

        영상을_저장한다(영상(히밥_ID, 맥도날드_ID));
        영상을_저장한다(영상(히밥_ID, 교촌치킨_ID));
        영상을_저장한다(영상(성시경_ID, 미스터피자_ID));

        var 예상_결과 = 예상되는_단일_셀럽_조회_응답(히밥, 2);

        // when
        var 응답 = 단일_셀럽_조회_요청(히밥_ID);

        // then
        단일_셀럽_조회_결과를_검증한다(예상_결과, 응답);
    }
}
