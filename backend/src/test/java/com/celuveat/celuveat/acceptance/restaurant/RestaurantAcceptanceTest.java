package com.celuveat.celuveat.acceptance.restaurant;

import static com.celuveat.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.단일_음식점_조회_결과를_검증한다;
import static com.celuveat.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.단일_음식점_조회_요청;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.미스터피자;

import com.celuveat.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
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
}
