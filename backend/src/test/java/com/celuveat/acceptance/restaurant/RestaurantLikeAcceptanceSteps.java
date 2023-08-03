package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.restaurant.application.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantLike;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

public class RestaurantLikeAcceptanceSteps {

    public static ExtractableResponse<Response> 로그인을_요청한다() {
        return given()
                .when().get("/api/oauth/login/kakao?code=abcd")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 좋아요_요청을_보낸다(Restaurant 맛집, String 세션_아이디) {
        return given(세션_아이디)
                .when().post("/api/restaurants/" + 맛집.id() + "/like")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 좋아요한_음식점_조회_요청(String 세션_아이디) {
        return given(세션_아이디)
                .when().get("/api/restaurants/like")
                .then().log().all()
                .extract();
    }

    public static List<RestaurantLikeQueryResponse> 예상_응답(List<RestaurantQueryResponse> 전체_음식점) {
        List<RestaurantLikeQueryResponse> expected = new ArrayList<>();
        for (RestaurantQueryResponse restaurantQueryResponse : 전체_음식점) {
            if (isLikedRestaurant(restaurantQueryResponse.name())) {
                expected.add(toRestaurantLikeQueryResponse(restaurantQueryResponse));
            }
        }
        return expected;
    }

    private static boolean isLikedRestaurant(String name) {
        List<String> likedRestaurants = List.of("말랑1호점", "말랑3호점", "도기2호점", "로이스2호점");
        return likedRestaurants.contains(name);
    }

    private static RestaurantLikeQueryResponse toRestaurantLikeQueryResponse(
            RestaurantQueryResponse restaurantQueryResponse
    ) {
        return new RestaurantLikeQueryResponse(
                restaurantQueryResponse.id(),
                restaurantQueryResponse.name(),
                restaurantQueryResponse.category(),
                restaurantQueryResponse.roadAddress(),
                restaurantQueryResponse.latitude(),
                restaurantQueryResponse.longitude(),
                restaurantQueryResponse.phoneNumber(),
                restaurantQueryResponse.naverMapUrl(),
                restaurantQueryResponse.celebs(),
                restaurantQueryResponse.images()
        );
    }

    public static void 응답_상태를_검증한다(ExtractableResponse<Response> 응답, HttpStatus 상태) {
        assertThat(응답.statusCode()).isEqualTo(상태.value());
    }

    public static void 결과를_검증한다(Optional<RestaurantLike> 결과) {
        assertThat(결과).isPresent();
    }

    public static void 결과를_검증한다(ExtractableResponse<Response> 응답, List<RestaurantLikeQueryResponse> 예상_응답) {
        List<RestaurantLikeQueryResponse> responseBody = 응답.as(new TypeRef<>() {
        });
        assertThat(responseBody).usingRecursiveComparison().isEqualTo(예상_응답);
    }
}
