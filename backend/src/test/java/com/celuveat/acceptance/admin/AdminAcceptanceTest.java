package com.celuveat.acceptance.admin;

import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.데이터_입력_생성;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.데이터_저장_요청;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.셀럽_입력_생성;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.셀럽_저장_요청;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.줄바꿈;
import static com.celuveat.celeb.fixture.CelebFixture.셀럽;

import com.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("어드민 인수 테스트")
class AdminAcceptanceTest extends AcceptanceTest {

    @Nested
    class 음식점_데이터_저장 {

        @Test
        void 엑셀_스프레드_시트에서_긁은_데이터를_DB에_저장한다() {
            // given
            셀럽을_저장한다(셀럽("도기"));
            셀럽을_저장한다(셀럽("로이스"));
            셀럽을_저장한다(셀럽("말랑"));
            셀럽을_저장한다(셀럽("오도"));

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
            데이터_수_검증(4, celebRepository);
            데이터_수_검증(4, restaurantRepository);
            데이터_수_검증(6, restaurantImageRepository);
            데이터_수_검증(6, videoRepository);
        }

        @Test
        void 사진_이름에_두_장_이상을_입력할_수_있다() {
            // given
            셀럽을_저장한다(셀럽("도기"));
            String 입력_데이터 = 데이터_입력_생성("도기", "test1.jpeg, test2.jpeg", "insta1, insta2");

            // when
            데이터_저장_요청(입력_데이터);

            // then
            데이터_수_검증(2, restaurantImageRepository);
        }
    }

    @Nested
    class 셀럽을_저장한다 {

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
            데이터_수_검증(4, celebRepository);
        }
    }
}
