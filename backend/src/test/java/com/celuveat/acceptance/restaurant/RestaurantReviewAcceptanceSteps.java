package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.util.Base64Util;
import com.celuveat.restaurant.presentation.dto.ReportReviewRequest;
import com.celuveat.restaurant.presentation.dto.SaveReviewRequest;
import com.celuveat.restaurant.presentation.dto.UpdateReviewRequest;
import com.celuveat.restaurant.query.dto.RestaurantReviewsQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantReviewsQueryResponse.RestaurantReviewSingleQueryResponse;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class RestaurantReviewAcceptanceSteps {

    public static SaveReviewRequest 리뷰_요청_데이터(String 내용, Long 음식점_아이디, Double 평점) {
        return 리뷰_요청_데이터(내용, 음식점_아이디, 평점, Collections.emptyList());
    }

    public static SaveReviewRequest 리뷰_요청_데이터(String 내용, Long 음식점_아이디, Double 평점, List<String> images) {
        List<MultipartFile> list = images.stream()
                .map(RestaurantReviewAcceptanceSteps::multipartFile)
                .toList();
        return new SaveReviewRequest(내용, 음식점_아이디, 평점, list);
    }

    private static MultipartFile multipartFile(String name) {
        return new MockMultipartFile(
                name, name, "multipart/form-data", name.getBytes()
        );
    }

    public static ExtractableResponse<Response> 리뷰_작성_요청(String 세션_아이디, SaveReviewRequest 요청) {
        var content = new MultiPartSpecBuilder(요청.content())
                .controlName("content")
                .charset(UTF_8)
                .build();
        var restaurantId = new MultiPartSpecBuilder(요청.restaurantId())
                .controlName("restaurantId")
                .charset(UTF_8)
                .build();
        var rating = new MultiPartSpecBuilder(요청.rating())
                .controlName("rating")
                .charset(UTF_8).build();
        RequestSpecification requestSpecification = given(세션_아이디)
                .multiPart(content)
                .multiPart(restaurantId)
                .multiPart(rating);
        int 사진_수 = 요청.images().size();
        for (int i = 0; i < 사진_수; i++) {
            requestSpecification.multiPart(멀티파트_스팩을_추출한다(요청, i));
        }
        return requestSpecification
                .contentType("multipart/form-data")
                .when().post("/reviews")
                .then().log().all()
                .extract();
    }

    private static MultiPartSpecification 멀티파트_스팩을_추출한다(SaveReviewRequest 요청, int index) {
        var image = 요청.images().get(index);
        try {
            return new MultiPartSpecBuilder(image.getBytes())
                    .controlName("images")
                    .fileName(image.getName())
                    .charset(UTF_8)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ExtractableResponse<Response> 리뷰_조회_요청(String 세션_아이디, Long 음식점_아이디) {
        return given(세션_아이디)
                .queryParam("restaurantId", 음식점_아이디)
                .when().get("/reviews")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_조회_요청(Long 음식점_아이디) {
        return 리뷰_조회_요청(null, 음식점_아이디);
    }

    public static RestaurantReviewsQueryResponse 리뷰_조회_결과(
            int 리뷰_총_개수,
            RestaurantReviewSingleQueryResponse... 단일_리뷰_데이터들
    ) {
        return new RestaurantReviewsQueryResponse(리뷰_총_개수, Arrays.asList(단일_리뷰_데이터들));
    }

    public static RestaurantReviewSingleQueryResponse 단일_리뷰_데이터(
            OauthMember 회원,
            String 리뷰_내용,
            double 리뷰_평점,
            int 좋아요_수,
            boolean 좋아요_여부,
            String... 이미지_이름들
    ) {
        return new RestaurantReviewSingleQueryResponse(null,
                회원.id(), 회원.nickname(), 회원.profileImageUrl(),
                리뷰_내용,
                null,
                좋아요_수,
                좋아요_여부,
                리뷰_평점,
                Arrays.stream(이미지_이름들)
                        .map(Base64Util::encode)
                        .toList()
        );
    }

    public static RestaurantReviewSingleQueryResponse 단일_리뷰_데이터(
            OauthMember 회원,
            String 리뷰_내용,
            double 리뷰_평점,
            String... 이미지_이름들
    ) {
        return 단일_리뷰_데이터(회원, 리뷰_내용, 리뷰_평점, 0, false, 이미지_이름들);
    }

    public static void 음식점_리뷰_조회_응답을_검증한다(
            RestaurantReviewsQueryResponse 예상_응답,
            ExtractableResponse<Response> 응답
    ) {
        RestaurantReviewsQueryResponse responseBody = 응답.as(RestaurantReviewsQueryResponse.class);
        assertThat(responseBody).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(예상_응답);
    }

    public static UpdateReviewRequest 리뷰_수정_요청_데이터(String 내용, double 평점) {
        return new UpdateReviewRequest(내용, 평점);
    }

    public static ExtractableResponse<Response> 리뷰_수정_요청(
            String 세션_아이디,
            Long 리뷰_아이디,
            UpdateReviewRequest 요청
    ) {
        return given(세션_아이디)
                .body(요청)
                .when().patch("/reviews/" + 리뷰_아이디)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_삭제_요청(
            String 세션_아이디,
            Long 리뷰_아이디
    ) {
        return given(세션_아이디)
                .when().delete("/reviews/" + 리뷰_아이디)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 좋아요_요청(String 세션_아이디, Long 리뷰_아이디) {
        return given(세션_아이디)
                .when().post("/reviews/{reviewID}/like", 리뷰_아이디)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_신고_요청(
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
