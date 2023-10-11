package com.celuveat.acceptance.auth;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MemberAcceptanceSteps {

    public static ExtractableResponse<Response> 회원정보_조회_요청(String 세션_아이디) {
        return given(세션_아이디)
                .when().get("/members/my")
                .then()
                .log().all()
                .extract();
    }
}
