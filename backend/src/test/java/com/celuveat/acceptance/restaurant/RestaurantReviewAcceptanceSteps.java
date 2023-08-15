package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.restaurant.application.dto.RestaurantReviewQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantReviewSingleResponse;
import com.celuveat.restaurant.presentation.dto.SaveReviewRequest;
import com.celuveat.restaurant.presentation.dto.UpdateReviewRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;

public class RestaurantReviewAcceptanceSteps {

    public static SaveReviewRequest 리뷰_요청(String 내용) {
        return new SaveReviewRequest(내용);
    }

    public static UpdateReviewRequest 리뷰_수정_요청(String 내용) {
        return new UpdateReviewRequest(내용);
    }

    public static RestaurantReviewQueryResponse 예상_응답() {
        return new RestaurantReviewQueryResponse(4, List.of(
                new RestaurantReviewSingleResponse(null, "말랑", "abc", "리뷰4", null),
                new RestaurantReviewSingleResponse(null, "말랑", "abc", "리뷰3", null),
                new RestaurantReviewSingleResponse(null, "로이스", "abc", "리뷰2", null),
                new RestaurantReviewSingleResponse(null, "도기", "abc", "리뷰1", null)
        ));
    }

    public static ExtractableResponse<Response> 리뷰_작성_요청을_보낸다(SaveReviewRequest 요청, String 세션_아이디, Long 음식점_아이디) {
        return given(세션_아이디)
                .body(요청)
                .when().post("/api/restaurants/" + 음식점_아이디 + "/reviews")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_조회_요청을_보낸다(Long 음식점_아이디) {
        return given()
                .when().get("/api/restaurants/" + 음식점_아이디 + "/reviews")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_수정_요청을_보낸다(
            UpdateReviewRequest 요청,
            String 세션_아이디,
            Long 음식점_아이디,
            Long 리뷰_아이디
    ) {
        return given(세션_아이디)
                .body(요청)
                .when().patch("/api/restaurants/" + 음식점_아이디 + "/reviews/" + 리뷰_아이디)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_삭제_요청을_보낸다(
            String 세션_아이디,
            Long 음식점_아이디,
            Long 리뷰_아이디
    ) {
        return given(세션_아이디)
                .when().delete("/api/restaurants/" + 음식점_아이디 + "/reviews/" + 리뷰_아이디)
                .then().log().all()
                .extract();
    }

    public static void 응답을_검증한다(
            ExtractableResponse<Response> 응답,
            RestaurantReviewQueryResponse 예상_응답
    ) {
        RestaurantReviewQueryResponse responseBody = 응답.as(RestaurantReviewQueryResponse.class);
        assertThat(responseBody).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(예상_응답);
    }
}
