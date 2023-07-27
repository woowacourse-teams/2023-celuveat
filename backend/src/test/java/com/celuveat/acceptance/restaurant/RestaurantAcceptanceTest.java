package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.셀럽_전체_조회_요청;
import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.셀럽들;
import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.특정_이름의_셀럽을_찾는다;
import static com.celuveat.acceptance.common.AcceptanceSteps.없음;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.검색_영역;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.예상_응답;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_조건;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.조회_결과를_검증한다;
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
        void 모든_음식점을_조회한다() {
            // given
            var 예상_응답 = seedData.insertSeedData();

            // when
            var 응답 = 음식점_검색_요청(음식점_검색_조건(없음, 없음, 없음), 검색_영역(없음));

            // then
            조회_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 음식점_검색_조건으로_검색한다() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 셀럽들 = 셀럽들(셀럽_전체_조회_요청());
            var 말랑 = 특정_이름의_셀럽을_찾는다(셀럽들, "말랑");
            var 예상_응답 = 예상_응답(전체_음식점, 말랑.id(), 없음, "말 랑 ", 없음);

            // when
            var 응답 = 음식점_검색_요청(음식점_검색_조건(말랑.id(), 없음, "말 랑 "), 검색_영역(없음));

            // then
            조회_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 음식점_및_거리_검색_조건으로_검색한다() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 셀럽들 = 셀럽들(셀럽_전체_조회_요청());
            var 말랑 = 특정_이름의_셀럽을_찾는다(셀럽들, "말랑");
            var 예상_응답 = 예상_응답(전체_음식점, 말랑.id(), 없음, "말 랑 ", 검색_영역(박스_1번_지점포함));

            // when
            var 응답 = 음식점_검색_요청(음식점_검색_조건(말랑.id(), 없음, "말 랑 "), 검색_영역(박스_1번_지점포함));

            // then
            조회_결과를_검증한다(예상_응답, 응답);
        }
    }
}
