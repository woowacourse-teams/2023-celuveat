package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static com.celuveat.restaurant.util.RestaurantQueryTestUtils.celebQueryResponses;
import static com.celuveat.restaurant.util.RestaurantQueryTestUtils.restaurantImageQueryResponses;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;

public class RestaurantLikeAcceptanceSteps {

    public static ExtractableResponse<Response> 좋아요_요청을_보낸다(String 세션_아이디, Long 맛집_아이디) {
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
