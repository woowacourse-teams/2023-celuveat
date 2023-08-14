package com.celuveat.acceptance.auth;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.application.dto.MemberQueryResponse;
import com.celuveat.auth.domain.OauthMember;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ProfileAcceptanceSteps {

    public static MemberQueryResponse 예상_응답(OauthMember 멤버) {
        return new MemberQueryResponse(멤버.id(), 멤버.nickname(), 멤버.profileImageUrl());
    }

    public static ExtractableResponse<Response> 회원정보_조회를_요청한다(String 세션_아이디) {
        return given(세션_아이디)
                .when().get("/api/profile")
                .then().extract();
    }

    public static void 응답을_검증한다(ExtractableResponse<Response> 응답, MemberQueryResponse 예상_응답) {
        MemberQueryResponse responseBody = 응답.as(MemberQueryResponse.class);
        assertThat(responseBody).isEqualTo(예상_응답);
    }
}