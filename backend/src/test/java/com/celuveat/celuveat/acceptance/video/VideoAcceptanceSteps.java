package com.celuveat.celuveat.acceptance.video;

import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.given;
import static com.celuveat.celuveat.video.fixture.VideoFixture.toFindAllVideoByRestaurantIdResponse;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.video.application.dto.FindAllVideoByRestaurantIdResponse;
import com.celuveat.celuveat.video.domain.Video;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class VideoAcceptanceSteps {

    public static FindAllVideoByRestaurantIdResponse 셀럽의_영상(Celeb 셀럽, Video 영상) {
        return toFindAllVideoByRestaurantIdResponse(영상, 셀럽);
    }

    public static List<FindAllVideoByRestaurantIdResponse> 예상되는_영상_조회_응답(
            FindAllVideoByRestaurantIdResponse... 영상들
    ) {
        return Arrays.stream(영상들).toList();
    }

    public static ExtractableResponse<Response> 음식점_ID로_영상_조회_요청(Long 음식점_ID) {
        return given()
                .queryParam("restaurantId", 음식점_ID)
                .when().get("/videos")
                .then().log().all()
                .extract();
    }

    public static void 음식점_ID로_조회한_영상을_검증한다(
            List<FindAllVideoByRestaurantIdResponse> 예상,
            ExtractableResponse<Response> 실제_응답
    ) {
        List<FindAllVideoByRestaurantIdResponse> responseBody = 실제_응답.body().as(new TypeRef<>() {
        });
        assertThat(responseBody).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(예상);
    }
}
