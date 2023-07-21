package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;

public class RestaurantAcceptanceSteps {

    public static ExtractableResponse<Response> 음식점_전체_조회_요청() {
        return given()
                .when().get("/api/restaurants")
                .then().log().all()
                .extract();
    }

    public static void 조회_결과를_검증한다(List<RestaurantQueryResponse> 예상_응답, ExtractableResponse<Response> 응답) {
        List<RestaurantQueryResponse> restaurantQueryResponse = 응답.as(new TypeRef<>() {
        });
        assertThat(restaurantQueryResponse).usingRecursiveComparison()
                .isEqualTo(예상_응답);
    }
}
