package com.celuveat.celuveat.acceptance.restaurant;

import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.restaurant.domain.Restaurant;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class RestaurantAcceptanceSteps {

    public static ExtractableResponse<Response> 단일_음식점_조회_요청(Long 음식점_ID) {
        return given()
                .when()
                .get("/restaurants/{id}", 음식점_ID)
                .then().log().all()
                .extract();
    }

    public static void 단일_음식점_조회_결과를_검증한다(Restaurant 예상_응답, ExtractableResponse<Response> 응답) {
        assertThat(응답.as(Restaurant.class))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(예상_응답);
    }
}
