package com.celuveat.celuveat.acceptance.restaurant;

import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.common.page.PageResponse;
import com.celuveat.celuveat.restaurant.application.dto.RestaurantSearchResponse;
import com.celuveat.celuveat.restaurant.domain.Restaurant;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class RestaurantAcceptanceSteps {

    public static ExtractableResponse<Response> 단일_음식점_조회_요청(Long 음식점_ID) {
        return given()
                .when()
                .get("/restaurants/{id}", 음식점_ID)
                .then().log().all()
                .extract();
    }

    public static void 단일_음식점_조회_결과를_검증한다(Restaurant 예상_응답, ExtractableResponse<Response> 응답) {
        assertThat(응답.as(Restaurant.class))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(예상_응답);
    }

    public static ExtractableResponse<Response> 셀럽_ID로_음식점_검색_요청(Long 히밥_ID, int 페이지, int 한_페이지에_보여줄_음식점_수) {
        return given()
                .queryParam("celebId", 히밥_ID)
                .queryParam("page", 페이지)
                .queryParam("size", 한_페이지에_보여줄_음식점_수)
                .when().get("/restaurants")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 셀럽_ID로_음식점_검색_요청(Long 히밥_ID) {
        return given()
                .queryParam("celebId", 히밥_ID)
                .when().get("/restaurants")
                .then().log().all()
                .extract();
    }

    public static RestaurantSearchResponse 음식점_검색_결과(Restaurant 음식점) {
        return new RestaurantSearchResponse(
                음식점.id(),
                음식점.imageUrl(),
                음식점.name(),
                음식점.category(),
                음식점.roadAddress(),
                음식점.addressLotNumber(),
                음식점.zipCode(),
                음식점.latitude(),
                음식점.longitude(),
                음식점.phoneNumber(),
                false);
    }

    public static void 음식점_검색_결과_검증(
            PageResponse<RestaurantSearchResponse> 예상,
            ExtractableResponse<Response> 응답
    ) {
        PageResponse<RestaurantSearchResponse> result = 응답.as(new TypeRef<>() {
        });
        assertThat(result).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(예상);
    }
}
