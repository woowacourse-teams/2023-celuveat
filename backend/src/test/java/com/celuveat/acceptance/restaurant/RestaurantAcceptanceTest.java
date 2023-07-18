package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_전체_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.조회_결과를_검증한다;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.common.SeedData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("Restaurant 인수테스트")
public class RestaurantAcceptanceTest extends AcceptanceTest {

    @Autowired
    private SeedData seedData;

    @Test
    void 모든_음식점을_조회한다() {
        // given
        var 예상_응답 = seedData.insertSeedData();

        // when
        var 응답 = 음식점_전체_조회_요청();

        // then
        조회_결과를_검증한다(예상_응답, 응답);
    }
}
