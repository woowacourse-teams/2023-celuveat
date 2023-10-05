package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.restaurant.presentation.dto.SaveReviewRequest;
import com.celuveat.restaurant.presentation.dto.UpdateReviewRequest;
import com.celuveat.restaurant.query.dto.RestaurantReviewQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantReviewSingleResponse;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class RestaurantReviewAcceptanceSteps {

    public static SaveReviewRequest 리뷰_요청(String 내용, Long 음식점_아이디, Double 평점) {
        return new SaveReviewRequest(내용, 음식점_아이디, 평점, Collections.emptyList());
    }

    public static SaveReviewRequest 리뷰_요청(String 내용, Long 음식점_아이디, Double 평점, List<MultipartFile> images) {
        return new SaveReviewRequest(내용, 음식점_아이디, 평점, images);
    }

    public static UpdateReviewRequest 리뷰_수정_요청(String 내용, Double 평점) {
        return new UpdateReviewRequest(내용, 평점);
    }

    public static RestaurantReviewQueryResponse 예상_응답(Long 말랑_아이디, Long 로이스_아이디, Long 도기_아이디) {
        return new RestaurantReviewQueryResponse(4, List.of(
                new RestaurantReviewSingleResponse(null, 말랑_아이디, "말랑", "abc", "리뷰4", null, 0, false, 5.0, null),
                new RestaurantReviewSingleResponse(null, 말랑_아이디, "말랑", "abc", "리뷰3", null, 0, false, 5.0, null),
                new RestaurantReviewSingleResponse(null, 로이스_아이디, "로이스", "abc", "리뷰2", null, 0, false, 5.0, null),
                new RestaurantReviewSingleResponse(null, 도기_아이디, "도기", "abc", "리뷰1", null, 0, false, 5.0, null)
        ));
    }

    public static ExtractableResponse<Response> 리뷰_작성_요청을_보낸다(SaveReviewRequest 요청, String 세션_아이디) {
        var content = new MultiPartSpecBuilder(요청.content()).controlName("content").charset(UTF_8).build();
        var restaurantId =
                new MultiPartSpecBuilder(요청.restaurantId()).controlName("restaurantId").charset(UTF_8).build();
        var rating = new MultiPartSpecBuilder(요청.rating()).controlName("rating").charset(UTF_8).build();
        return given(세션_아이디)
                .multiPart(content)
                .multiPart(restaurantId)
                .multiPart(rating)
                .contentType("multipart/form-data")
                .when().post("/reviews")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 사진_2장이_포함된_리뷰_작성_요청을_보낸다(SaveReviewRequest 요청, String 세션_아이디)
            throws IOException {
        var content = new MultiPartSpecBuilder(요청.content()).controlName("content").charset(UTF_8).build();
        var restaurantId =
                new MultiPartSpecBuilder(요청.restaurantId()).controlName("restaurantId").charset(UTF_8).build();
        var rating = new MultiPartSpecBuilder(요청.rating()).controlName("rating").charset(UTF_8).build();
        var imagesA = 멀티파트_스팩을_추출한다(요청, 0);
        var imagesB = 멀티파트_스팩을_추출한다(요청, 1);
        return given(세션_아이디)
                .multiPart(content)
                .multiPart(restaurantId)
                .multiPart(rating)
                .multiPart(imagesA)
                .multiPart(imagesB)
                .contentType("multipart/form-data")
                .when().post("/reviews")
                .then().log().all()
                .extract();
    }

    private static MultiPartSpecification 멀티파트_스팩을_추출한다(
            SaveReviewRequest 요청,
            int index
    ) throws IOException {
        var image = 요청.images().get(index);
        return new MultiPartSpecBuilder(image.getBytes())
                .controlName("images")
                .fileName(image.getOriginalFilename())
                .charset(UTF_8)
                .build();
    }

    public static ExtractableResponse<Response> 리뷰_조회_요청을_보낸다(Long 음식점_아이디) {
        return given()
                .queryParam("restaurantId", 음식점_아이디)
                .when().get("/reviews")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_조회_요청을_보낸다(Long 음식점_아이디, String 세션_아이디) {
        return given(세션_아이디)
                .queryParam("restaurantId", 음식점_아이디)
                .when().get("/reviews")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_수정_요청을_보낸다(
            UpdateReviewRequest 요청,
            String 세션_아이디,
            Long 리뷰_아이디
    ) {
        return given(세션_아이디)
                .body(요청)
                .when().patch("/reviews/" + 리뷰_아이디)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_삭제_요청을_보낸다(
            String 세션_아이디,
            Long 리뷰_아이디
    ) {
        return given(세션_아이디)
                .when().delete("/reviews/" + 리뷰_아이디)
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

    public static MockMultipartFile 이미지를_생성한다(final String name, final String originalFilename) {
        return new MockMultipartFile(
                name, originalFilename, "multipart/form-data", originalFilename.getBytes()
        );
    }
}
