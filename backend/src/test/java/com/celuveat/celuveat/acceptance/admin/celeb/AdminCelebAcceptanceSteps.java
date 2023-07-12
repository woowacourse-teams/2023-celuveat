package com.celuveat.celuveat.acceptance.admin.celeb;

import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.given;

import com.celuveat.celuveat.admin.presentation.dto.RegisterCelebRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class AdminCelebAcceptanceSteps {

    public static RegisterCelebRequest 등록할_셀럽_정보(
            String 이름,
            String 유튜브_채널_ID,
            String 유튜브_채널_이름,
            int 구독자_수,
            String 유튜브_채널_URL,
            String 배경사진_URL,
            String 프로필사진_URL
    ) {
        return new RegisterCelebRequest(
                이름,
                유튜브_채널_ID,
                유튜브_채널_이름,
                구독자_수,
                유튜브_채널_URL,
                배경사진_URL,
                프로필사진_URL);
    }

    public static ExtractableResponse<Response> 셀럽_등록_요청(String 세션_ID, RegisterCelebRequest 등록할_셀럽_정보) {
        return given()
                .cookie("JSESSIONID", 세션_ID)
                .body(등록할_셀럽_정보)
                .when().post("/admin/celebs")
                .then().log().all()
                .extract();
    }
}
