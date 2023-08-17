package com.celuveat.acceptance.video;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static com.celuveat.celeb.exception.CelebExceptionType.NOT_FOUND_CELEB;
import static com.celuveat.restaurant.exception.RestaurantExceptionType.NOT_FOUND_RESTAURANT;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.celeb.exception.CelebException;
import com.celuveat.common.PageResponse;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.exception.RestaurantException;
import com.celuveat.video.application.dto.VideoWithCelebQueryResponse;
import com.celuveat.video.domain.Video;
import com.celuveat.video.presentation.dto.VideoSearchCondRequest;
import com.celuveat.video.utils.VideoResponseUtils;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoAcceptanceSteps {

    public static ExtractableResponse<Response> 영상_조회_요청(
            VideoSearchCondRequest videoSearchCondRequest
    ) {
        Map<String, Object> param = new HashMap<>();
        param.put("restaurantId", videoSearchCondRequest.restaurantId());
        param.put("celebId", videoSearchCondRequest.celebId());
        return given()
                .queryParams(param)
                .when().get("/api/videos")
                .then().log().all()
                .extract();
    }

    public static Restaurant 특정_이름의_음식점을_찾는다(List<Video> 영상들, String 음식점_이름) {
        return 영상들.stream()
                .map(Video::restaurant)
                .filter(restaurant -> restaurant.name().equals(음식점_이름))
                .findAny()
                .orElseThrow(() -> new RestaurantException(NOT_FOUND_RESTAURANT));
    }

    public static List<Video> 특정_음식점의_영상을_추출한다(List<Video> 영상들, Restaurant 음식점) {
        return 영상들.stream()
                .filter(video -> video.restaurant().id().equals(음식점.id()))
                .toList();
    }

    public static Celeb 특정_이름의_셀럽을_찾는다(List<Video> 영상들, String 셀럽_이름) {
        return 영상들.stream()
                .map(Video::celeb)
                .filter(celeb -> celeb.name().equals(셀럽_이름))
                .findAny()
                .orElseThrow(() -> new CelebException(NOT_FOUND_CELEB));
    }

    public static List<Video> 특정_셀럽의_영상을_추출한다(List<Video> 영상들, Celeb 셀럽) {
        return 영상들.stream()
                .filter(video -> video.celeb().id().equals(셀럽.id()))
                .toList();
    }

    public static List<VideoWithCelebQueryResponse> 영상_조회_예상_응답(List<Video> 영상들) {
        return 영상들.stream()
                .map(VideoAcceptanceSteps::toVideoWithCelebQueryResponse)
                .toList();
    }

    public static void 영상_응답_결과를_검증한다(List<VideoWithCelebQueryResponse> 예상_응답, ExtractableResponse<Response> 응답) {
        PageResponse<VideoWithCelebQueryResponse> response = 응답.as(new TypeRef<>() {
        });
        assertThat(response.content())
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(예상_응답);
    }

    private static VideoWithCelebQueryResponse toVideoWithCelebQueryResponse(Video video) {
        Celeb celeb = video.celeb();
        return new VideoWithCelebQueryResponse(
                video.id(),
                VideoResponseUtils.extractVideoKey(video.youtubeUrl()),
                video.uploadDate(),
                celeb.id(),
                celeb.name(),
                celeb.youtubeChannelName(),
                celeb.profileImageUrl()
        );
    }
}
