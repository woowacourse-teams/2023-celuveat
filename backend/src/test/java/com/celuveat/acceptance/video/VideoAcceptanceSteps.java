package com.celuveat.acceptance.video;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.common.PageResponse;
import com.celuveat.video.command.domain.Video;
import com.celuveat.video.presentation.dto.VideoSearchCondRequest;
import com.celuveat.video.query.dto.VideoQueryResponse;
import com.celuveat.video.utils.VideoResponseUtil;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoAcceptanceSteps {

    public static VideoSearchCondRequest 영상_조회_요청_데이터(
            Object 셀럽_ID,
            Object 음식점_ID
    ) {
        return new VideoSearchCondRequest((Long) 셀럽_ID, (Long) 음식점_ID);
    }

    public static ExtractableResponse<Response> 영상_조회_요청(VideoSearchCondRequest 검색_조건) {
        Map<String, Object> param = new HashMap<>();
        param.put("restaurantId", 검색_조건.restaurantId());
        param.put("celebId", 검색_조건.celebId());
        return given()
                .queryParams(param)
                .when().get("/videos")
                .then()
                .log().all()
                .extract();
    }

    public static List<VideoQueryResponse> 영상_조회_응답(Video... 영상들) {
        return Arrays.stream(영상들)
                .map(VideoAcceptanceSteps::toVideoWithCelebQueryResponse)
                .toList();
    }

    public static VideoQueryResponse toVideoWithCelebQueryResponse(Video video) {
        Celeb celeb = video.celeb();
        return new VideoQueryResponse(
                video.id(),
                VideoResponseUtil.extractVideoKey(video.youtubeUrl()),
                video.uploadDate(),
                celeb.id(),
                celeb.name(),
                celeb.youtubeChannelName(),
                celeb.profileImageUrl()
        );
    }

    public static void 영상_응답_결과를_검증한다(List<VideoQueryResponse> 예상_응답, ExtractableResponse<Response> 응답) {
        PageResponse<VideoQueryResponse> response = 응답.as(new TypeRef<>() {
        });
        assertThat(response.content())
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(예상_응답);
    }
}
