package com.celuveat.celuveat.acceptance.restaurant;

import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.다음_페이지_없음;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.다음_페이지_있음;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.페이지_결과;
import static com.celuveat.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.단일_음식점_조회_결과를_검증한다;
import static com.celuveat.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.단일_음식점_조회_요청;
import static com.celuveat.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.셀럽_ID로_음식점_검색_요청;
import static com.celuveat.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_결과;
import static com.celuveat.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_결과_검증;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.미스터피자;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.celuveat.video.fixture.VideoFixture.영상;

import com.celuveat.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Restaurant 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
public class RestaurantAcceptanceTest extends AcceptanceTest {

    @Test
    void 단일_음식점을_조회한다() {
        // given
        var 미스터피자_ID = 음식점을_저장한다(미스터피자());
        var 예상_응답 = 미스터피자();

        // when
        var 응답 = 단일_음식점_조회_요청(미스터피자_ID);

        // then
        단일_음식점_조회_결과를_검증한다(예상_응답, 응답);
    }

    @Nested
    class 셀럽_ID로_음식점을_조회할_때 {

        @Test
        void 다음_페이지_있는_경우() {
            // given
            var 히밥 = 히밥();
            var 히밥_ID = 셀럽을_저장한다(히밥);

            var 맛집1 = 음식점("맛집1");
            var 맛집2 = 음식점("맛집2");
            var 맛집3 = 음식점("맛집3");
            var 맛집4 = 음식점("맛집4");
            var 맛집5 = 음식점("맛집5");
            var 맛집1_ID = 음식점을_저장한다(맛집1);
            var 맛집2_ID = 음식점을_저장한다(맛집2);
            var 맛집3_ID = 음식점을_저장한다(맛집3);
            var 맛집4_ID = 음식점을_저장한다(맛집4);
            var 맛집5_ID = 음식점을_저장한다(맛집5);

            영상을_저장한다(영상(히밥_ID, 맛집1_ID));
            영상을_저장한다(영상(히밥_ID, 맛집2_ID));
            영상을_저장한다(영상(히밥_ID, 맛집3_ID));
            영상을_저장한다(영상(히밥_ID, 맛집4_ID));
            영상을_저장한다(영상(히밥_ID, 맛집5_ID));

            var 예상 = 페이지_결과(
                    다음_페이지_있음,
                    음식점_검색_결과(맛집5),
                    음식점_검색_결과(맛집4)
            );

            // when
            var 응답 = 셀럽_ID로_음식점_검색_요청(히밥_ID, 1, 2);

            // then
            음식점_검색_결과_검증(예상, 응답);
        }

        @Test
        void 다음_페이지_없는_경우() {
            // given
            var 히밥 = 히밥();
            var 성시경 = 성시경();
            var 히밥_ID = 셀럽을_저장한다(히밥);
            var 성시경_ID = 셀럽을_저장한다(성시경);

            var 맛집1 = 음식점("맛집1");
            var 맛집2 = 음식점("맛집2");
            var 맛집5 = 음식점("맛집5");
            var 맛집1_ID = 음식점을_저장한다(맛집1);
            var 맛집2_ID = 음식점을_저장한다(맛집2);
            var 맛집3_ID = 음식점을_저장한다(음식점("맛집3"));
            var 맛집4_ID = 음식점을_저장한다(음식점("맛집4"));
            var 맛집5_ID = 음식점을_저장한다(맛집5);

            영상을_저장한다(영상(히밥_ID, 맛집1_ID));
            영상을_저장한다(영상(히밥_ID, 맛집2_ID));
            영상을_저장한다(영상(성시경_ID, 맛집3_ID));
            영상을_저장한다(영상(성시경_ID, 맛집4_ID));
            영상을_저장한다(영상(히밥_ID, 맛집5_ID));

            var 예상 = 페이지_결과(
                    다음_페이지_없음,
                    음식점_검색_결과(맛집5),
                    음식점_검색_결과(맛집2),
                    음식점_검색_결과(맛집1)
            );

            // when
            var 응답 = 셀럽_ID로_음식점_검색_요청(히밥_ID, 1, 3);

            // then
            음식점_검색_결과_검증(예상, 응답);
        }
    }
}
