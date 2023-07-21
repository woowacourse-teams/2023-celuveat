package com.celuveat.acceptance.celeb;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.fixture.CelebFixture;
import com.celuveat.celeb.presentation.response.FindAllCelebResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;

public class CelebAcceptanceSteps {

    public static List<FindAllCelebResponse> 예상_셀럽조회_결과(String... 셀럽들_이름) {
        return Arrays.stream(셀럽들_이름)
                .map(CelebFixture::셀럽)
                .map(FindAllCelebResponse::from)
                .toList();
    }

    public static ExtractableResponse<Response> 셀럽_전체_조회_요청() {
        return given()
                .when()
                .get("/api/celebs")
                .then().log().all()
                .extract();
    }

    public static void 셀럽_전체_조회_결과를_검증한다(List<FindAllCelebResponse> 예상, ExtractableResponse<Response> 응답) {
        var result = 응답.as(new TypeRef<>() {
        });
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(예상);
    }
}
