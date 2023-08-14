package com.celuveat.acceptance.admin;

import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.데이터_입력_생성;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.데이터_저장_요청;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.셀럽_입력_생성;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.셀럽_저장_요청;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.셀럽_저장_요청_생성;
import static com.celuveat.admin.exception.AdminExceptionType.EXIST_NULL;
import static com.celuveat.admin.exception.AdminExceptionType.INVALID_URL_PATTERN;
import static com.celuveat.celeb.fixture.CelebFixture.셀럽;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.admin.exception.AdminException;
import com.celuveat.celeb.domain.Celeb;
import com.celuveat.common.exception.BaseExceptionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("어드민 인수 테스트")
class AdminAcceptanceTest extends AcceptanceTest {

    private final String 줄바꿈 = System.lineSeparator();

    @Nested
    class 음식점_데이터_저장 {

        @Test
        void 엑셀_스프레드_시트에서_긁은_데이터를_DB에_저장한다() {
            // given
            셀럽_저장(셀럽("도기"));
            셀럽_저장(셀럽("로이스"));
            셀럽_저장(셀럽("말랑"));
            셀럽_저장(셀럽("오도"));

            String 입력_데이터 = 데이터_입력_생성("도기", "국민연금")
                    + 줄바꿈
                    + 데이터_입력_생성("도기", "농민백암순대")
                    + 줄바꿈
                    + 데이터_입력_생성("로이스", "신천직화")
                    + 줄바꿈
                    + 데이터_입력_생성("오도", "국민연금")
                    + 줄바꿈
                    + 데이터_입력_생성("말랑", "바스버거")
                    + 줄바꿈
                    + 데이터_입력_생성("말랑", "신천직화");

            // when
            데이터_저장_요청(입력_데이터);

            // then
            셀럽_수_검증(4);
            음식점_수_검증(4);
            음식점_이미지_수_검증(6);
            영상_수_검증(6);
        }

        @Test
        void 사진_이름에_두_장_이상을_입력할_수_있다() {
            // given
            셀럽_저장(셀럽("도기"));
            String 입력_데이터 = 데이터_입력_생성("도기", "test1.jpeg, test2.jpeg");

            // when
            데이터_저장_요청(입력_데이터);

            // then
            음식점_이미지_수_검증(2);
        }
    }

    @Nested
    class 셀럽_저장 {

        @Test
        void 셀럽을_저장한다() {
            // given
            String 입력_데이터 = 셀럽_입력_생성("도기")
                    + 줄바꿈
                    + 셀럽_입력_생성("말랑")
                    + 줄바꿈
                    + 셀럽_입력_생성("오도")
                    + 줄바꿈
                    + 셀럽_입력_생성("로이스");

            // when
            셀럽_저장_요청(입력_데이터);

            // then
            셀럽_수_검증(4);
        }

        @Test
        void 셀럽_이름이_누락되면_예외가_발생한다() {
            // given
            String input = "\t@도기\thttps://이미지";

            // then
            BaseExceptionType exceptionType = assertThrows(AdminException.class, () -> 셀럽_저장_요청_생성(input))
                    .exceptionType();
            assertThat(exceptionType).isEqualTo(EXIST_NULL);
        }

        @Test
        void 셀럽의_유튜브_채널_이름이_누락되면_예외가_발생한다() {
            // given
            String input = "도기\t\thttps://이미지";

            // then
            BaseExceptionType exceptionType = assertThrows(AdminException.class, () -> 셀럽_저장_요청_생성(input))
                    .exceptionType();
            assertThat(exceptionType).isEqualTo(EXIST_NULL);
        }

        @Test
        void 셀럽의_프로필_사진_URL이_누락되면_예외가_발생한다() {
            // given
            String input = "도기\t@도기\t";

            // then
            BaseExceptionType exceptionType = assertThrows(AdminException.class, () -> 셀럽_저장_요청_생성(input))
                    .exceptionType();
            assertThat(exceptionType).isEqualTo(EXIST_NULL);
        }

        @Test
        void 데이터_순서가_다르면_예외가_발생한다() {
            // given
            String input = "https://\t@도기\t@도기";

            // then
            BaseExceptionType exceptionType = assertThrows(AdminException.class, () -> 셀럽_저장_요청_생성(input))
                    .exceptionType();
            assertThat(exceptionType).isEqualTo(INVALID_URL_PATTERN);
        }
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
