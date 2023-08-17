package com.celuveat.acceptance.auth;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.application.dto.MemberProfileResponse;
import com.celuveat.auth.domain.OauthMember;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MemberAcceptanceSteps {

    public static MemberProfileResponse 예상_응답(OauthMember 멤버) {
        return new MemberProfileResponse(멤버.id(), 멤버.nickname(), 멤버.profileImageUrl());
    }

    public static ExtractableResponse<Response> 회원정보_조회를_요청한다(String 세션_아이디) {
        return given(세션_아이디)
                .when().get("/api/members/my")
                .then().extract();
    }

    public static void 응답을_검증한다(ExtractableResponse<Response> 응답, MemberProfileResponse 예상_응답) {
        MemberProfileResponse responseBody = 응답.as(MemberProfileResponse.class);
        assertThat(responseBody).isEqualTo(예상_응답);
    }

    public static ExtractableResponse<Response> 로그아웃_요청을_보낸다(String 세션_아이디, String oauthServerType) {
        return given(세션_아이디)
                .when().get("/api/oauth/logout/{oauthServerType}", oauthServerType)
                .then().extract();
    }

    public static ExtractableResponse<Response> 회원_탈퇴를_한다(String 세션_아이디, String oauthServerType) {
        return given(세션_아이디)
                .when().delete("/api/oauth/withdraw/{oauthServerType}", oauthServerType)
                .then().extract();
    }
}
