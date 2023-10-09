package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class RestaurantReviewLikeAcceptanceSteps {

    public static ExtractableResponse<Response> 좋아요_요청을_보낸다(String 세션_아이디, Long 리뷰_아이디) {
        return given(세션_아이디)
                .when().post("/reviews/{reviewID}/like", 리뷰_아이디)
                .then().log().all()
                .extract();
    }
}
