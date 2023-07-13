package com.celuveat.celuveat.acceptance.admin;

import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.given;

import com.celuveat.celuveat.admin.application.dto.AdminLoginResponse;
import com.celuveat.celuveat.admin.presentation.dto.AdminLoginRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class AdminAuthAcceptanceSteps {

    public static ExtractableResponse<Response> 관리자_로그인_요청(String 아이디, String 비밀번호) {
        var adminLoginRequest = new AdminLoginRequest(아이디, 비밀번호);
        return given()
                .body(adminLoginRequest)
                .when().post("/admin/login")
                .then().log().all()
                .extract();
    }

    public static String 세션_ID를_추출한다(ExtractableResponse<Response> 로그인_응답) {
        AdminLoginResponse response = 로그인_응답.as(AdminLoginResponse.class);
        return response.sessionId();
    }

    public static String 관리자로_로그인하고_세션_ID를_받아온다(String 아이디, String 비밀번호) {
        var 응답 = 관리자_로그인_요청(아이디, 비밀번호);
        return 세션_ID를_추출한다(응답);
    }
}
