package com.celuveat.acceptance.admin;

import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.데이터_저장_요청;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.입력_생성;
import static com.celuveat.celeb.fixture.CelebFixture.셀럽;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("Admin 인수테스트")
@DisplayNameGeneration(ReplaceUnderscores.class)
class AdminAcceptanceTest extends AcceptanceTest {

    @Test
    void 엑셀_스프레드_시트에서_긁은_데이터를_DB에_저장한다() {
        // given
        celebRepository.save(셀럽("도기"));
        celebRepository.save(셀럽("로이스"));
        celebRepository.save(셀럽("말랑"));
        celebRepository.save(셀럽("오도"));

        String 입력_데이터 = 입력_생성("도기", "국민연금")
                + System.lineSeparator()
                + 입력_생성("도기", "농민백암순대")
                + System.lineSeparator()
                + 입력_생성("로이스", "신천직화")
                + System.lineSeparator()
                + 입력_생성("오도", "국민연금")
                + System.lineSeparator()
                + 입력_생성("말랑", "바스버거")
                + System.lineSeparator()
                + 입력_생성("말랑", "신천직화");

        // when
        데이터_저장_요청(입력_데이터);

        // then
        assertThat(celebRepository.count()).isEqualTo(4);
        assertThat(restaurantRepository.count()).isEqualTo(4);
        assertThat(restaurantImageRepository.count()).isEqualTo(6);
        assertThat(videoRepository.count()).isEqualTo(6);
    }
}
