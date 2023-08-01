package com.celuveat.acceptance.restaurant;

import static com.celuveat.auth.presentation.AuthConstant.JSESSION_ID;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantLike;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Optional;
import org.springframework.http.HttpStatus;

public class RestaurantLikeAcceptanceSteps {

    public static ExtractableResponse<Response> 로그인을_요청한다() {
        return RestAssured
                .given().log().all()
                .when().get("/api/oauth/login/kakao?code=abcd")
                .then().log().all()
                .extract();
    }
    
    public static ExtractableResponse<Response> 좋아요_요청을_보낸다(Restaurant 맛집, String 세션_아이디) {
        return RestAssured
                .given().log().all()
                .cookie(JSESSION_ID, 세션_아이디)
                .when().post("/api/restaurants/" + 맛집.id() + "/like")
                .then().log().all()
                .extract();
    }

    public static void 응답_상태를_검증한다(ExtractableResponse<Response> 응답, HttpStatus 상태) {
        assertThat(응답.statusCode()).isEqualTo(상태.value());
    }

    public static void 결과를_검증한다(Optional<RestaurantLike> 결과) {
        assertThat(결과).isPresent();
    }
}
