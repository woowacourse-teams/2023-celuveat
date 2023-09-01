package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.restaurant.query.RestaurantEntityManagerQueryRepositoryImpl.LocationSearchCond;
import com.celuveat.restaurant.query.RestaurantEntityManagerQueryRepositoryImpl.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSimpleResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantLikeAcceptanceSteps {

    public static void 음식점들에_좋아요를_누른다(List<Long> 좋아요_누를_음식점_아이디, String 세션_아이디) {
        좋아요_누를_음식점_아이디.forEach(id -> 좋아요_요청을_보낸다(id, 세션_아이디));
    }

    public static ExtractableResponse<Response> 로그인을_요청한다() {
        return given()
                .when().get("/api/oauth/login/kakao?code=abcd")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 좋아요_요청을_보낸다(Long 맛집_아이디, String 세션_아이디) {
        return given(세션_아이디)
                .when().post("/api/restaurants/" + 맛집_아이디 + "/like")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 좋아요한_음식점_조회_요청(String 세션_아이디) {
        return given(세션_아이디)
                .when().get("/api/restaurants/like")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 회원으로_음식점_검색_요청(
            RestaurantSearchCond 음식점_검색_조건,
            LocationSearchCond 위치_검색_조건,
            String 세션_아이디
    ) {
        Map<String, Object> param = new HashMap<>();
        param.put("celebId", 음식점_검색_조건.celebId());
        param.put("category", 음식점_검색_조건.category());
        param.put("restaurantName", 음식점_검색_조건.restaurantName());
        param.put("lowLatitude", 위치_검색_조건.lowLatitude());
        param.put("highLatitude", 위치_검색_조건.highLatitude());
        param.put("lowLongitude", 위치_검색_조건.lowLongitude());
        param.put("highLongitude", 위치_검색_조건.highLongitude());
        return given(세션_아이디)
                .queryParams(param)
                .when().get("/api/restaurants")
                .then().log().all()
                .extract();
    }

    public static RestaurantLikeQueryResponse toRestaurantLikeQueryResponse(
            RestaurantSimpleResponse restaurantSimpleResponse
    ) {
        return new RestaurantLikeQueryResponse(
                restaurantSimpleResponse.id(),
                restaurantSimpleResponse.name(),
                restaurantSimpleResponse.category(),
                restaurantSimpleResponse.roadAddress(),
                restaurantSimpleResponse.latitude(),
                restaurantSimpleResponse.longitude(),
                restaurantSimpleResponse.phoneNumber(),
                restaurantSimpleResponse.naverMapUrl(),
                restaurantSimpleResponse.celebs(),
                restaurantSimpleResponse.images()
        );
    }

    public static void 좋아요한_음식점_조회_요청_결과를_검증한다(ExtractableResponse<Response> 응답,
                                               List<RestaurantLikeQueryResponse> 예상_응답) {
        List<RestaurantLikeQueryResponse> responseBody = 응답.as(new TypeRef<>() {
        });
        assertThat(responseBody).usingRecursiveComparison().isEqualTo(예상_응답);
    }
}
