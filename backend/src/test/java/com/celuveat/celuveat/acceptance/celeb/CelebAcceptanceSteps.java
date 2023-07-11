package com.celuveat.celuveat.acceptance.celeb;

import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.given;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.toFindCelebByIdResponse;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.application.dto.FindAllCelebResponse;
import com.celuveat.celuveat.celeb.application.dto.FindCelebByIdResponse;
import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.celeb.fixture.CelebFixture;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class CelebAcceptanceSteps {

    public static ExtractableResponse<Response> 단일_셀럽_조회_요청(Long 히밥_ID) {
        return given()
                .when()
                .get("/celebs/{id}", 히밥_ID)
                .then().log().all()
                .extract();
    }

    public static FindCelebByIdResponse 예상되는_단일_셀럽_조회_응답(Celeb 셀럽, int 음식점_수) {
        return toFindCelebByIdResponse(셀럽, 음식점_수);
    }

    public static void 단일_셀럽_조회_결과를_검증한다(
            FindCelebByIdResponse 예상,
            ExtractableResponse<Response> 응답
    ) {
        FindCelebByIdResponse response = 응답.as(FindCelebByIdResponse.class);
        assertThat(response).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(예상);
    }

    public static List<FindAllCelebResponse> 예상되는_전체_셀럽_조회_응답(Celeb... 셀럽들) {
        return Arrays.stream(셀럽들)
                .map(CelebFixture::toFindAllCelebResponse)
                .toList();
    }

    public static ExtractableResponse<Response> 전체_셀럽_조회_요청() {
        return given()
                .when().get("/celebs")
                .then().log().all()
                .extract();
    }

    public static void 전체_셀럽_조회_결과를_검증한다(
            List<FindAllCelebResponse> 예상,
            ExtractableResponse<Response> 실제_응답
    ) {
        List<FindAllCelebResponse> responseBody = 실제_응답.body().as(new TypeRef<>() {
        });
        assertThat(responseBody).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(예상);
    }
}
