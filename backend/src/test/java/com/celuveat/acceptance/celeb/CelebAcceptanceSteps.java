package com.celuveat.acceptance.celeb;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.fixture.CelebFixture;
import com.celuveat.celeb.query.dto.CelebQueryResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class CelebAcceptanceSteps {

    public static List<CelebQueryResponse> 예상_셀럽조회_결과(String... 셀럽들_이름) {
        return Arrays.stream(셀럽들_이름)
                .map(CelebFixture::셀럽)
                .map(CelebQueryResponse::from)
                .toList();
    }

    public static ExtractableResponse<Response> 셀럽_전체_조회_요청() {
        return given()
                .when()
                .get("/celebs")
                .then()
                .extract();
    }

    public static List<CelebQueryResponse> 셀럽들만_추출_한다(
            ExtractableResponse<Response> 응답
    ) {
        return 응답.as(new TypeRef<>() {
        });
    }

    public static CelebQueryResponse 특정_이름의_셀럽을_찾는다(List<CelebQueryResponse> 셀럽들, String 셀럽) {
        return 셀럽들.stream()
                .filter(it -> it.name().equals(셀럽))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    public static void 셀럽_전체_조회_결과를_검증한다(List<CelebQueryResponse> 예상, ExtractableResponse<Response> 응답) {
        List<CelebQueryResponse> result = 응답.as(new TypeRef<>() {
        });
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(예상);
    }
}
