package com.celuveat.acceptance.admin;

import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.데이터_저장_요청;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.입력_생성;
import static com.celuveat.celeb.fixture.CelebFixture.셀럽;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.celeb.domain.Celeb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("Admin 인수테스트")
@DisplayNameGeneration(ReplaceUnderscores.class)
class AdminAcceptanceTest extends AcceptanceTest {

    private static final String 줄바꿈 = System.lineSeparator();

    @Test
    void 엑셀_스프레드_시트에서_긁은_데이터를_DB에_저장한다() {
        // given
        셀럽_저장(셀럽("도기"));
        셀럽_저장(셀럽("로이스"));
        셀럽_저장(셀럽("말랑"));
        셀럽_저장(셀럽("오도"));

        String 입력_데이터 = 입력_생성("도기", "국민연금")
                + 줄바꿈
                + 입력_생성("도기", "농민백암순대")
                + 줄바꿈
                + 입력_생성("로이스", "신천직화")
                + 줄바꿈
                + 입력_생성("오도", "국민연금")
                + 줄바꿈
                + 입력_생성("말랑", "바스버거")
                + 줄바꿈
                + 입력_생성("말랑", "신천직화");

        // when
        데이터_저장_요청(입력_데이터);

        // then
        셀럽_수_검증(4);
        음식점_수_검증(4);
        음식점_이미지_수_검증(6);
        영상_수_검증(6);
    }

    private void 셀럽_저장(Celeb 셀럽) {
        celebRepository.save(셀럽);
    }

    private void 셀럽_수_검증(int expected) {
        assertThat(celebRepository.count()).isEqualTo(expected);
    }

    private void 음식점_수_검증(int expected) {
        assertThat(restaurantRepository.count()).isEqualTo(expected);
    }

    private void 음식점_이미지_수_검증(int expected) {
        assertThat(restaurantImageRepository.count()).isEqualTo(expected);
    }

    private void 영상_수_검증(int expected) {
        assertThat(videoRepository.count()).isEqualTo(expected);
    }
}
