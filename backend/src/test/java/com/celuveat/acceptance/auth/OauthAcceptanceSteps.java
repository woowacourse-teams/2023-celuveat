package com.celuveat.acceptance.auth;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.query.dto.OauthMemberProfileResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class OauthAcceptanceSteps {

    public static ExtractableResponse<Response> 리다이렉트_URL을_요청한다(String oauthServerType) {
        return given()
                .when().get("/oauth/{oauthServerType}", oauthServerType)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인을_요청한다(String authCode) {
        return given()
                .when().get("/oauth/login/kakao?code={authCode}", authCode)
                .then()
                .extract();
    }

    public static OauthMemberProfileResponse 예상_응답(OauthMember 멤버) {
        return new OauthMemberProfileResponse(
                멤버.id(),
                멤버.nickname(),
                멤버.profileImageUrl(),
                멤버.oauthId().oauthServer().name()
        );
    }

    public static void 응답을_검증한다(ExtractableResponse<Response> 응답, OauthMemberProfileResponse 예상_응답) {
        OauthMemberProfileResponse responseBody = 응답.as(OauthMemberProfileResponse.class);
        assertThat(responseBody).isEqualTo(예상_응답);
    }

    public static ExtractableResponse<Response> 로그아웃_요청을_보낸다(String 세션_아이디, String oauthServerType) {
        return given(세션_아이디)
                .when().get("/oauth/logout/{oauthServerType}", oauthServerType)
                .then().extract();
    }

    public static ExtractableResponse<Response> 회원_탈퇴를_한다(String 세션_아이디, String oauthServerType) {
        return given(세션_아이디)
                .when().delete("/oauth/withdraw/{oauthServerType}", oauthServerType)
                .then().extract();
    }

    public static void 응답에_JSESSIONID_헤더가_존재한다(ExtractableResponse<Response> 응답) {
        assertThat(응답.header("Set-Cookie")).startsWith("JSESSIONID");
    }
}
