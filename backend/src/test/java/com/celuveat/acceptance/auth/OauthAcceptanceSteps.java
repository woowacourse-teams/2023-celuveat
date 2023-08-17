package com.celuveat.acceptance.auth;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class OauthAcceptanceSteps {

    public static ExtractableResponse<Response> 로그아웃_요청을_보낸다(String 세션_아이디) {
        return given(세션_아이디)
                .when().get("/api/oauth/logout/kakao")
                .then().extract();
    }

    public static ExtractableResponse<Response> 회원탈퇴_요청을_보낸다(String 세션_아이디) {
        return given(세션_아이디)
                .when().delete("/api/oauth/withdraw/kakao")
                .then().extract();
    }
}
