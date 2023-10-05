package com.celuveat.acceptance.celeb;

import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.셀럽_전체_조회_결과를_검증한다;
import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.셀럽_전체_조회_요청;
import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.예상_셀럽조회_결과;

import com.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("셀럽 인수테스트")
public class CelebAcceptanceTest extends AcceptanceTest {

    @Test
    void 모든_셀럽들을_조회한다() {
        // given
        셀럽들을_저장한다("말랑", "로이스", "오도", "도기", "도담", "제레미", "푸만능");

        // when
        var 응답 = 셀럽_전체_조회_요청();

        // then
        var 예상 = 예상_셀럽조회_결과("말랑", "로이스", "오도", "도기", "도담", "제레미", "푸만능");
        셀럽_전체_조회_결과를_검증한다(예상, 응답);
    }
}
