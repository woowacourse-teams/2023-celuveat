package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.RestaurantSearchCondRequest;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchQueryResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
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
            RestaurantSearchQueryResponse restaurantSearchQueryResponse
    ) {
        return new LikedRestaurantQueryResponse(
                restaurantSearchQueryResponse.getId(),
                restaurantSearchQueryResponse.getName(),
                restaurantSearchQueryResponse.getCategory(),
                restaurantSearchQueryResponse.getRoadAddress(),
                restaurantSearchQueryResponse.getLatitude(),
                restaurantSearchQueryResponse.getLongitude(),
                restaurantSearchQueryResponse.getPhoneNumber(),
                restaurantSearchQueryResponse.getNaverMapUrl(),
                restaurantSearchQueryResponse.getCelebs(),
                restaurantSearchQueryResponse.getImages()
        );
    }

    public static void 좋아요한_음식점_조회_요청_결과를_검증한다(ExtractableResponse<Response> 응답,
                                               List<LikedRestaurantQueryResponse> 예상_응답) {
        List<LikedRestaurantQueryResponse> responseBody = 응답.as(new TypeRef<>() {
        });
        assertThat(responseBody).usingRecursiveComparison().isEqualTo(예상_응답);
    }

    public static List<LikedRestaurantQueryResponse> 예상_응답(List<RestaurantSearchQueryResponse> 전체_음식점) {
        RestaurantSearchQueryResponse restaurantSearchQueryResponse1 = 전체_음식점.get(1);
        RestaurantSearchQueryResponse restaurantSearchQueryResponse2 = 전체_음식점.get(3);
        RestaurantSearchQueryResponse restaurantSearchQueryResponse3 = 전체_음식점.get(4);
        RestaurantSearchQueryResponse restaurantSearchQueryResponse4 = 전체_음식점.get(7);
        return new ArrayList<>(List.of(
                toRestaurantLikeQueryResponse(restaurantSearchQueryResponse4),
                toRestaurantLikeQueryResponse(restaurantSearchQueryResponse3),
                toRestaurantLikeQueryResponse(restaurantSearchQueryResponse2),
                toRestaurantLikeQueryResponse(restaurantSearchQueryResponse1)
        ));
    }

    public static List<RestaurantSearchQueryResponse> 좋아요_포함된_예상_응답(
            List<RestaurantSearchQueryResponse> 전체_음식점) {
        RestaurantSearchQueryResponse restaurantSearchQueryResponse1 = 전체_음식점.get(1);
        RestaurantSearchQueryResponse restaurantSearchQueryResponse2 = 전체_음식점.get(3);
        RestaurantSearchQueryResponse restaurantSearchQueryResponse3 = 전체_음식점.get(4);
        RestaurantSearchQueryResponse restaurantSearchQueryResponse4 = 전체_음식점.get(7);
        List<RestaurantSearchQueryResponse> expected = new ArrayList<>(전체_음식점);
        expected.set(1, increaseLikeCount(changeIsLikedToTrue(restaurantSearchQueryResponse1)));
        expected.set(3, increaseLikeCount(changeIsLikedToTrue(restaurantSearchQueryResponse2)));
        expected.set(4, increaseLikeCount(changeIsLikedToTrue(restaurantSearchQueryResponse3)));
        expected.set(7, increaseLikeCount(changeIsLikedToTrue(restaurantSearchQueryResponse4)));
        return expected;
    }

    public static RestaurantSearchQueryResponse changeIsLikedToTrue(
            RestaurantSearchQueryResponse restaurantSearchQueryResponse) {
        return new RestaurantSearchQueryResponse(
                restaurantSearchQueryResponse.id(),
                restaurantSearchQueryResponse.name(),
                restaurantSearchQueryResponse.category(),
                restaurantSearchQueryResponse.superCategory(),
                restaurantSearchQueryResponse.roadAddress(),
                restaurantSearchQueryResponse.latitude(),
                restaurantSearchQueryResponse.longitude(),
                restaurantSearchQueryResponse.phoneNumber(),
                restaurantSearchQueryResponse.naverMapUrl(),
                restaurantSearchQueryResponse.viewCount(),
                restaurantSearchQueryResponse.likeCount(),
                true,
                restaurantSearchQueryResponse.rating(),
                restaurantSearchQueryResponse.distance(),
                restaurantSearchQueryResponse.celebs(),
                restaurantSearchQueryResponse.images()
        );
    }

    public static RestaurantSearchQueryResponse increaseLikeCount(
            RestaurantSearchQueryResponse restaurantSearchQueryResponse) {
        return new RestaurantSearchQueryResponse(
                restaurantSearchQueryResponse.id(),
                restaurantSearchQueryResponse.name(),
                restaurantSearchQueryResponse.category(),
                restaurantSearchQueryResponse.superCategory(),
                restaurantSearchQueryResponse.roadAddress(),
                restaurantSearchQueryResponse.latitude(),
                restaurantSearchQueryResponse.longitude(),
                restaurantSearchQueryResponse.phoneNumber(),
                restaurantSearchQueryResponse.naverMapUrl(),
                restaurantSearchQueryResponse.viewCount(),
                restaurantSearchQueryResponse.likeCount() + 1,
                restaurantSearchQueryResponse.isLiked(),
                restaurantSearchQueryResponse.rating(),
                restaurantSearchQueryResponse.distance(),
                restaurantSearchQueryResponse.celebs(),
                restaurantSearchQueryResponse.images()
        );
    }

    public static List<Long> 좋아요_누를_음식점_아이디를_뽑는다(List<RestaurantSearchQueryResponse> 전체_음식점) {
        return List.of(
                전체_음식점.get(1).id(),
                전체_음식점.get(3).id(),
                전체_음식점.get(4).id(),
                전체_음식점.get(7).id()
        );
    }
}
