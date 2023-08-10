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
import java.util.NoSuchElementException;

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

    public static List<FindAllCelebResponse> 셀럽들만_추출_한다(
            ExtractableResponse<Response> 응답
    ) {
        return 응답.as(new TypeRef<>() {
        });
    }

    public static FindAllCelebResponse 특정_이름의_셀럽을_찾는다(List<FindAllCelebResponse> 셀럽들, String 셀럽) {
        return 셀럽들.stream()
                .filter(it -> it.name().equals(셀럽))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    public static void 셀럽_전체_조회_결과를_검증한다(List<FindAllCelebResponse> 예상, ExtractableResponse<Response> 응답) {
        List<FindAllCelebResponse> result = 응답.as(new TypeRef<>() {
        });
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(예상);
    }
}
