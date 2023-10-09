package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;

import com.celuveat.restaurant.presentation.dto.ReportReviewRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class RestaurantReviewReportAcceptanceSteps {

    public static ExtractableResponse<Response> 신고_요청을_보낸다(
            String 세션_아이디,
            Long 리뷰_아이디,
            String 신고내용
    ) {
        ReportReviewRequest reportReviewRequest = new ReportReviewRequest(신고내용);
        return given(세션_아이디)
                .when().body(reportReviewRequest)
                .post("/reviews/{reviewId}/report", 리뷰_아이디)
                .then()
                .extract();
    }
}
