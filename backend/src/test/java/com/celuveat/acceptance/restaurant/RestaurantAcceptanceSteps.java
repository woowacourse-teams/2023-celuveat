package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static com.celuveat.restaurant.util.RestaurantQueryTestUtils.celebQueryResponses;
import static com.celuveat.restaurant.util.RestaurantQueryTestUtils.restaurantDetailQueryResponse;
import static com.celuveat.restaurant.util.RestaurantQueryTestUtils.restaurantImageQueryResponses;
import static com.celuveat.restaurant.util.RestaurantQueryTestUtils.restaurantSearchQueryResponse;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.common.PageResponse;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.RestaurantSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.SuggestCorrectionRequest;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchQueryResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantAcceptanceSteps {

    public static LocationSearchCondRequest 위치_검색_영역_요청_데이터(
            Object 최소_위도,
            Object 최대_위도,
            Object 최소_경도,
            Object 최대_경도
    ) {
        return new LocationSearchCondRequest(
                (Double) 최소_위도,
                (Double) 최대_위도,
                (Double) 최소_경도,
                (Double) 최대_경도
        );
    }

    public static RestaurantSearchCondRequest 음식점_검색_조건_요청_데이터(
            Object 셀럽_ID,
            Object 카테고리,
            Object 음식점_이름
    ) {
        return new RestaurantSearchCondRequest(
                (Long) 셀럽_ID,
                (String) 카테고리,
                (String) 음식점_이름
        );
    }

    public static ExtractableResponse<Response> 음식점_검색_요청(
            RestaurantSearchCondRequest 음식점_검색_조건,
            LocationSearchCondRequest 위치_검색_조건
    ) {
        return 음식점_검색_요청(null, 음식점_검색_조건, 위치_검색_조건);
    }

    public static ExtractableResponse<Response> 음식점_검색_요청(
            String 세션_ID,
            RestaurantSearchCondRequest 음식점_검색_조건,
            LocationSearchCondRequest 위치_검색_조건
    ) {
        Map<String, Object> param = new HashMap<>();
        param.put("celebId", 음식점_검색_조건.celebId());
        param.put("category", 음식점_검색_조건.category());
        param.put("restaurantName", 음식점_검색_조건.restaurantName());
        param.put("lowLatitude", 위치_검색_조건.lowLatitude());
        param.put("highLatitude", 위치_검색_조건.highLatitude());
        param.put("lowLongitude", 위치_검색_조건.lowLongitude());
        param.put("highLongitude", 위치_검색_조건.highLongitude());
        return given(세션_ID)
                .queryParams(param)
                .when().get("/restaurants")
                .then()
                .log().all()
                .extract();
    }


    public static ExtractableResponse<Response> 음식점_좋아요_정렬_검색_요청(
            RestaurantSearchCondRequest 음식점_검색_조건,
            LocationSearchCondRequest 위치_검색_조건
    ) {
        Map<String, Object> param = new HashMap<>();
        param.put("celebId", 음식점_검색_조건.celebId());
        param.put("category", 음식점_검색_조건.category());
        param.put("restaurantName", 음식점_검색_조건.restaurantName());
        param.put("lowLatitude", 위치_검색_조건.lowLatitude());
        param.put("highLatitude", 위치_검색_조건.highLatitude());
        param.put("lowLongitude", 위치_검색_조건.lowLongitude());
        param.put("highLongitude", 위치_검색_조건.highLongitude());
        param.put("sort", "like");
        return given()
                .queryParams(param)
                .when().get("/restaurants")
                .then()
                .log().all()
                .extract();
    }

    public static RestaurantSearchQueryResponse 음식점_검색_결과(
            Restaurant 음식점,
            boolean 좋아요_눌렀는지_여부, double 평점,
            List<Celeb> 셀럽들, List<RestaurantImage> 음식점_사진들
    ) {
        return restaurantSearchQueryResponse(음식점, 좋아요_눌렀는지_여부, 평점, 셀럽들, 음식점_사진들);
    }

    public static void 조회_결과를_검증한다(
            List<RestaurantSearchQueryResponse> 예상_응답,
            ExtractableResponse<Response> 응답
    ) {
        PageResponse<RestaurantSearchQueryResponse> 응답_결과 = 응답.as(new TypeRef<>() {
        });
        assertThat(응답_결과.content())
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .isEqualTo(예상_응답);
    }

    public static ExtractableResponse<Response> 음식점_상세_조회_요청(
            Long 음식점_ID,
            Long 셀럽_ID
    ) {
        return 음식점_상세_조회_요청(null, 음식점_ID, 셀럽_ID);
    }

    public static ExtractableResponse<Response> 음식점_상세_조회_요청(
            String 세션_ID,
            Long 음식점_ID,
            Long 셀럽_ID
    ) {
        Map<String, Object> param = new HashMap<>();
        param.put("celebId", 셀럽_ID);
        return given(세션_ID)
                .queryParams(param)
                .when().get("/restaurants/{restaurantId}", 음식점_ID)
                .then()
                .log().all()
                .extract();
    }

    public static RestaurantDetailQueryResponse 상세_조회_응답(
            Restaurant 음식점,
            boolean 좋아요_여부,
            double 평점,
            List<Celeb> 셀럽들, List<RestaurantImage> 음식점_사진들
    ) {
        return restaurantDetailQueryResponse(음식점, 좋아요_여부, 평점, 셀럽들, 음식점_사진들);
    }

    public static RestaurantDetailQueryResponse 상세_조회_응답(
            Restaurant 음식점,
            boolean 좋아요_여부,
            int 좋아요_수,
            double 평점,
            List<Celeb> 셀럽들, List<RestaurantImage> 음식점_사진들
    ) {
        return restaurantDetailQueryResponse(음식점, 좋아요_여부, 좋아요_수, 평점, 셀럽들, 음식점_사진들);
    }

    public static void 상세_조회_결과를_검증한다(
            RestaurantDetailQueryResponse 예상_응답,
            ExtractableResponse<Response> 응답
    ) {
        RestaurantDetailQueryResponse response = 응답.as(RestaurantDetailQueryResponse.class);
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(예상_응답);
    }

    public static ExtractableResponse<Response> 근처_음식점_조회_요청(Long 특정_음식점_ID, int 요청_거리) {
        return 근처_음식점_조회_요청(null, 특정_음식점_ID, 요청_거리);
    }

    public static ExtractableResponse<Response> 근처_음식점_조회_요청(String 세션_아이디, Long 특정_음식점_ID, int 요청_거리) {
        return given(세션_아이디)
                .queryParams("distance", 요청_거리)
                .when().get("/restaurants/{restaurantId}/nearby", 특정_음식점_ID)
                .then()
                .log().all()
                .extract();
    }

    public static void 특정_거리_이내에_있는_음식점인지_검증한다(
            List<RestaurantSearchQueryResponse> 예상_응답,
            ExtractableResponse<Response> 요청_결과,
            int 요청_거리
    ) {
        PageResponse<RestaurantSearchQueryResponse> pageResponse = 요청_결과.as(new TypeRef<>() {
        });
        assertThat(pageResponse.content())
                .hasSize(예상_응답.size())
                .allSatisfy(restaurant -> {
                    assertThat(restaurant.distance()).isLessThanOrEqualTo(요청_거리);
                })
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .isEqualTo(예상_응답);
    }

    public static ExtractableResponse<Response> 정보_수정_제안_요청(Long 음식점_ID, String... 수정_내용들) {
        return given()
                .body(new SuggestCorrectionRequest(Arrays.asList(수정_내용들)))
                .post("/restaurants/{id}/correction", 음식점_ID)
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 음식점_좋아요_요청(String 세션_아이디, Long 맛집_아이디) {
        return given(세션_아이디)
                .when().post("/restaurants/" + 맛집_아이디 + "/like")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 좋아요한_음식점_조회_요청(String 세션_아이디) {
        return given(세션_아이디)
                .when().get("/restaurants/like")
                .then()
                .log().all()
                .extract();
    }

    public static LikedRestaurantQueryResponse 좋아요한_음식점_검색_결과(
            Restaurant 음식점,
            int 좋아요_수,
            double 평점,
            List<Celeb> 셀럽들, List<RestaurantImage> 음식점_사진들
    ) {
        return new LikedRestaurantQueryResponse(
                음식점.id(),
                음식점.name(),
                음식점.category(),
                음식점.roadAddress(),
                음식점.latitude(),
                음식점.longitude(),
                음식점.phoneNumber(),
                음식점.naverMapUrl(),
                음식점.viewCount(),
                좋아요_수,
                평점,
                celebQueryResponses(음식점, 셀럽들),
                restaurantImageQueryResponses(음식점, 음식점_사진들)
        );
    }

    public static void 좋아요한_음식점_조회_결과를_검증한다(
            List<LikedRestaurantQueryResponse> 예상_응답,
            ExtractableResponse<Response> 응답
    ) {
        List<LikedRestaurantQueryResponse> response = 응답.as(new TypeRef<>() {
        });
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(예상_응답);
    }
}
