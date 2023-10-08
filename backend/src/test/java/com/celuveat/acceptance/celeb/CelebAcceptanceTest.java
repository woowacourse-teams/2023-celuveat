package com.celuveat.acceptance.celeb;

import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.셀럽_전체_조회_결과를_검증한다;
import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.셀럽_전체_조회_요청;
import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.예상_셀럽조회_결과;
import static com.celuveat.celeb.fixture.CelebFixture.맛객리우;
import static com.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celeb.fixture.CelebFixture.쯔양;
import static com.celuveat.celeb.fixture.CelebFixture.회사랑;

import com.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("셀럽 인수테스트")
public class CelebAcceptanceTest extends AcceptanceTest {

    @Test
    void 모든_셀럽들을_조회한다() {
        // given
        셀럽들을_저장한다(성시경(), 쯔양(), 맛객리우(), 회사랑());

        // when
        var 응답 = 셀럽_전체_조회_요청();

        // then
        var 예상 = 예상_셀럽조회_결과(성시경(), 쯔양(), 맛객리우(), 회사랑());
        셀럽_전체_조회_결과를_검증한다(예상, 응답);
    }
}
