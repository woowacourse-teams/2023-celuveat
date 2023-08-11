package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.셀럽_전체_조회_요청;
import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.셀럽들만_추출_한다;
import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.특정_이름의_셀럽을_찾는다;
import static com.celuveat.acceptance.common.AcceptanceSteps.없음;
import static com.celuveat.acceptance.common.AcceptanceSteps.잘못된_요청_예외를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.검색_영역;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.근처_음식점_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.상세_조회_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.상세_조회_예상_응답;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.예상_응답;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_조건;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_상세_조회_실패_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_상세_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.조회_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.조회수를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.특정_거리_이내에_있는_음식점이며_기준이_되는_음식점은_포함하지_않는지_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.특정_이름의_음식점을_찾는다;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1_2번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1번_지점포함;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.common.SeedData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("음식점 인수테스트")
public class RestaurantAcceptanceTest extends AcceptanceTest {

    @Autowired
    private SeedData seedData;

    @Nested
    class 음식점_검색 {

        @Test
        void 검색영역_조건으로_음식점을_조회한다() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 예상_응답 = 예상_응답(전체_음식점, 없음, 없음, 없음, 검색_영역(박스_1_2번_지점포함));

            // when
            var 응답 = 음식점_검색_요청(음식점_검색_조건(없음, 없음, 없음), 검색_영역(박스_1_2번_지점포함));

            // then
            조회_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 음식점_검색_조건으로_검색한다() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 셀럽들 = 셀럽들만_추출_한다(셀럽_전체_조회_요청());
            var 말랑 = 특정_이름의_셀럽을_찾는다(셀럽들, "말랑");
            var 예상_응답 = 예상_응답(전체_음식점, 말랑.id(), 없음, "말 랑 ", 검색_영역(박스_1번_지점포함));

            // when
            var 응답 = 음식점_검색_요청(음식점_검색_조건(말랑.id(), 없음, "말 랑 "), 검색_영역(박스_1번_지점포함));

            // then
            조회_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 음식점_및_검색영역_조건으로_검색한다() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 셀럽들 = 셀럽들만_추출_한다(셀럽_전체_조회_요청());
            var 말랑 = 특정_이름의_셀럽을_찾는다(셀럽들, "말랑");
            var 예상_응답 = 예상_응답(전체_음식점, 말랑.id(), 없음, "말 랑 ", 검색_영역(박스_1번_지점포함));

            // when
            var 응답 = 음식점_검색_요청(음식점_검색_조건(말랑.id(), 없음, "말 랑 "), 검색_영역(박스_1번_지점포함));

            // then
            조회_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 검색영역이_누락되면_예외가_발생한다() {
            // when
            var 응답 = 음식점_검색_요청(음식점_검색_조건(없음, 없음, 없음), 검색_영역(없음));

            // then
            잘못된_요청_예외를_검증한다(응답);
        }

        @Test
        void 특정_음식점을_기준으로_일정_거리_내에_있는_모든_음식점을_조회한다() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 음식점 = 전체_음식점.get(0);
            Long 음식점_ID = 음식점.id();
            int 요청_거리 = 2000;

            // when
            var 요청_결과 = 근처_음식점_조회_요청(음식점_ID, 요청_거리);

            // then
            특정_거리_이내에_있는_음식점이며_기준이_되는_음식점은_포함하지_않는지_검증한다(요청_결과, 요청_거리, 음식점_ID);
        }
    }

    @Nested
    class 음식점_상세_조회 {

        @Test
        void 음식점ID로_조회() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 조회_음식점 = 특정_이름의_음식점을_찾는다(전체_음식점, "로이스2호점");
            var 셀럽들 = 셀럽들만_추출_한다(셀럽_전체_조회_요청());
            var 로이스 = 특정_이름의_셀럽을_찾는다(셀럽들, "로이스");
            var 예상_응답 = 상세_조회_예상_응답(전체_음식점, 조회_음식점.id(), 로이스.id());

            // when
            var 응답 = 음식점_상세_조회_요청(조회_음식점.id(), 로이스.id());

            // then
            상세_조회_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 셀럽ID_없이_음식점을_상세_조회하면_예외가_발생한다() {
            // when
            var 조회_음식점ID = 1L;
            var 응답 = 음식점_상세_조회_요청(조회_음식점ID, (Long) 없음);

            // then
            잘못된_요청_예외를_검증한다(응답);
        }

        @Test
        void 조회수가_증가한다() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 조회_음식점 = 특정_이름의_음식점을_찾는다(전체_음식점, "로이스2호점");
            var 셀럽들 = 셀럽들만_추출_한다(셀럽_전체_조회_요청());
            var 로이스 = 특정_이름의_셀럽을_찾는다(셀럽들, "로이스");
            음식점_상세_조회_요청(조회_음식점.id(), 로이스.id());
            음식점_상세_조회_요청(조회_음식점.id(), 로이스.id());
            var 예상_조회수 = 3;

            // when
            var 응답 = 음식점_상세_조회_요청(조회_음식점.id(), 로이스.id());

            // then
            조회수를_검증한다(예상_조회수, 응답);
        }

        @Test
        void 실패하면_조회수는_증가하지_않는다() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 조회_음식점 = 특정_이름의_음식점을_찾는다(전체_음식점, "로이스2호점");
            var 셀럽들 = 셀럽들만_추출_한다(셀럽_전체_조회_요청());
            var 로이스 = 특정_이름의_셀럽을_찾는다(셀럽들, "로이스");
            음식점_상세_조회_요청(조회_음식점.id(), 로이스.id());
            음식점_상세_조회_실패_요청(조회_음식점);
            var 예상_조회수 = 2;

            // when
            var 응답 = 음식점_상세_조회_요청(조회_음식점.id(), 로이스.id());

            // then
            조회수를_검증한다(예상_조회수, 응답);
        }
    }
}
