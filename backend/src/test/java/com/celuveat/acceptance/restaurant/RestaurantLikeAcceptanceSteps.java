package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.RestaurantSearchCondRequest;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchResponse;
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
                .when().get("/oauth/login/kakao?code=abcd")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 좋아요_요청을_보낸다(Long 맛집_아이디, String 세션_아이디) {
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

    public static ExtractableResponse<Response> 회원으로_음식점_검색_요청(
            RestaurantSearchCondRequest 음식점_검색_조건,
            LocationSearchCondRequest 위치_검색_조건,
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
                .when().get("/restaurants")
                .then().log().all()
                .extract();
    }

    public static LikedRestaurantQueryResponse toRestaurantLikeQueryResponse(
            RestaurantSearchResponse restaurantSearchResponse
    ) {
        return new LikedRestaurantQueryResponse(
                restaurantSearchResponse.getId(),
                restaurantSearchResponse.getName(),
                restaurantSearchResponse.getCategory(),
                restaurantSearchResponse.getRoadAddress(),
                restaurantSearchResponse.getLatitude(),
                restaurantSearchResponse.getLongitude(),
                restaurantSearchResponse.getPhoneNumber(),
                restaurantSearchResponse.getNaverMapUrl(),
                restaurantSearchResponse.getCelebs(),
                restaurantSearchResponse.getImages()
        );
    }

    public static void 좋아요한_음식점_조회_요청_결과를_검증한다(ExtractableResponse<Response> 응답,
                                               List<LikedRestaurantQueryResponse> 예상_응답) {
        List<LikedRestaurantQueryResponse> responseBody = 응답.as(new TypeRef<>() {
        });
        assertThat(responseBody).usingRecursiveComparison().isEqualTo(예상_응답);
    }
}
