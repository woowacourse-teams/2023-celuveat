package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_전체_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.조회_결과를_검증한다;

import com.celuveat.common.SeedData;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@DisplayName("Restaurant 인수테스트")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestaurantAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private SeedData seedData;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

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
