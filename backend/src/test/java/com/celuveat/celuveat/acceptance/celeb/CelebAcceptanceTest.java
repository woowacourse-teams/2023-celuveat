package com.celuveat.celuveat.acceptance.celeb;

import static com.celuveat.celuveat.acceptance.celeb.CelebAcceptanceSteps.예상되는_전체_셀럽_조회_응답;
import static com.celuveat.celuveat.acceptance.celeb.CelebAcceptanceSteps.전체_셀럽_조회_결과를_검증한다;
import static com.celuveat.celuveat.acceptance.celeb.CelebAcceptanceSteps.전체_셀럽_조회_요청;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.정상_처리됨;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;

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
}
