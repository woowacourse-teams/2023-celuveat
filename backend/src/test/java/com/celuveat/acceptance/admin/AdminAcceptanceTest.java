package com.celuveat.acceptance.admin;

import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.데이터_입력_생성;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.데이터_저장_요청;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.셀럽_입력_생성;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.셀럽_저장_요청;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.음식점_이미지_저장_요청;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.음식점_이미지_저장_요청_데이터;
import static com.celuveat.acceptance.admin.AdminAcceptanceSteps.줄바꿈;
import static com.celuveat.acceptance.common.AcceptanceSteps.생성됨;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.찾을수_없음;
import static com.celuveat.celeb.fixture.CelebFixture.셀럽;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.restaurant.command.domain.Restaurant;
import org.junit.jupiter.api.BeforeEach;
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

    @Nested
    class 이미지를_저장한다 {

        private final Restaurant 대성집 = 대성집();

        @BeforeEach
        void setUp() {
            testData.addRestaurants(대성집);
            초기_데이터_저장();
        }

        @Test
        void 이미_저장되어_있는_음식점의_이미지를_추가한다() {
            // given
            var 음식점_이미지 = 음식점_이미지_저장_요청_데이터(대성집.id(), "오도", "instagram", "imageA");

            // when
            var 응답 = 음식점_이미지_저장_요청(음식점_이미지);

            // then
            응답_상태를_검증한다(응답, 생성됨);
        }

        @Test
        void 존재하지_않는_음식점_이미지를_저장하려하면_예외가_발생한다() {
            // given
            var 음식점_이미지 = 음식점_이미지_저장_요청_데이터(100L, "오도", "instagram", "imageA");

            // when
            var 응답 = 음식점_이미지_저장_요청(음식점_이미지);

            // then
            응답_상태를_검증한다(응답, 찾을수_없음);
        }
    }
}
