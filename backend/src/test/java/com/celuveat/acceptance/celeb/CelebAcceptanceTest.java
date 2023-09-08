package com.celuveat.acceptance.celeb;

import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.셀럽_전체_조회_결과를_검증한다;
import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.셀럽_전체_조회_요청;
import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.예상_셀럽조회_결과;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.celeb.command.domain.CelebRepository;
import com.celuveat.celeb.fixture.CelebFixture;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("셀럽 인수테스트")
public class CelebAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CelebRepository celebRepository;

    @Test
    void 모든_셀럽들을_조회한다() {
        // given
        셀럽들을_저장한다("말랑", "로이스", "오도", "도기", "도담", "제레미", "푸만능");
        var 예상 = 예상_셀럽조회_결과("말랑", "로이스", "오도", "도기", "도담", "제레미", "푸만능");

        // when
        var 응답 = 셀럽_전체_조회_요청();

        // then
        셀럽_전체_조회_결과를_검증한다(예상, 응답);
    }

    private void 셀럽들을_저장한다(String... 셀럽들_이름) {
        List<Celeb> list = Arrays.stream(셀럽들_이름)
                .map(CelebFixture::셀럽)
                .toList();
        celebRepository.saveAll(list);
    }
}
