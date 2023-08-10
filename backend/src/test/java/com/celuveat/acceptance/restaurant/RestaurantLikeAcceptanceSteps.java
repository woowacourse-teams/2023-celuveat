package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.restaurant.application.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpStatus;

public class RestaurantLikeAcceptanceSteps {

    public static void 음식점들에_좋아요를_누른다(List<Long> 좋아요_누를_음식점_아이디, String 세션_아이디) {
        좋아요_누를_음식점_아이디.forEach(id -> 좋아요_요청을_보낸다(id, 세션_아이디));
    }

    public static ExtractableResponse<Response> 로그인을_요청한다() {
        return given()
                .when().get("/api/oauth/login/kakao?code=abcd")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 좋아요_요청을_보낸다(Long 맛집_아이디, String 세션_아이디) {
        return given(세션_아이디)
                .when().post("/api/restaurants/" + 맛집_아이디 + "/like")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 좋아요한_음식점_조회_요청(String 세션_아이디) {
        return given(세션_아이디)
                .when().get("/api/restaurants/like")
                .then()
                .extract();
    }

    public static RestaurantLikeQueryResponse toRestaurantLikeQueryResponse(
            RestaurantQueryResponse restaurantQueryResponse
    ) {
        return new RestaurantLikeQueryResponse(
                restaurantQueryResponse.id(),
                restaurantQueryResponse.name(),
                restaurantQueryResponse.category(),
                restaurantQueryResponse.roadAddress(),
                restaurantQueryResponse.latitude(),
                restaurantQueryResponse.longitude(),
                restaurantQueryResponse.phoneNumber(),
                restaurantQueryResponse.naverMapUrl(),
                restaurantQueryResponse.celebs(),
                restaurantQueryResponse.images()
        );
    }

    public static void 좋아요한_음식점_조회_요청_결과를_검증한다(ExtractableResponse<Response> 응답,
                                               List<RestaurantLikeQueryResponse> 예상_응답) {
        List<RestaurantLikeQueryResponse> responseBody = 응답.as(new TypeRef<>() {
        });
        assertThat(responseBody).usingRecursiveComparison().isEqualTo(예상_응답);
    }
}
