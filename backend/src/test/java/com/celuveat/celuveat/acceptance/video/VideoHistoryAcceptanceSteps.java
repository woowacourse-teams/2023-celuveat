package com.celuveat.celuveat.acceptance.video;

import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.video.application.dto.FindAllVideoHistoryResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class VideoHistoryAcceptanceSteps {

    public static ExtractableResponse<Response> 모든_영상_이력_조회() {
        return given()
                .when().get("/videos/history")
                .then().log().all()
                .extract();
    }

    public static void 조회한_모든_영상_이력을_검증한다(
            List<FindAllVideoHistoryResponse> 예상,
            ExtractableResponse<Response> 응답
    ) {
        List<FindAllVideoHistoryResponse> responseBody = 응답.body().as(new TypeRef<>() {
        });
        assertThat(responseBody).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(예상);
    }
}
