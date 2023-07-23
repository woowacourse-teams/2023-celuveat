package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.util.StringUtil;
import com.celuveat.restaurant.application.dto.CelebQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantAcceptanceSteps {

    public static ExtractableResponse<Response> 음식점_검색_요청(
            RestaurantSearchCond 검색_조건
    ) {
        Map<String, Object> param = new HashMap<>();
        param.put("celebId", 검색_조건.celebId());
        param.put("category", 검색_조건.category());
        param.put("restaurantName", 검색_조건.restaurantName());
        return given()
                .queryParams(param)
                .when().get("/api/restaurants")
                .then().log().all()
                .extract();
    }

    public static RestaurantSearchCond 검색_조건(
            Object 셀럽_ID,
            Object 카테고리,
            Object 음식점_이름,
            Object 위도,
            Object 경도,
            Object 스케일
    ) {
        return new RestaurantSearchCond(
                (Long) 셀럽_ID,
                (String) 카테고리,
                (String) 음식점_이름,
                (String) 위도,
                (String) 경도,
                (Integer) 스케일
        );
    }

    public static void 조회_결과를_검증한다(List<RestaurantQueryResponse> 예상_응답, ExtractableResponse<Response> 응답) {
        List<RestaurantQueryResponse> restaurantQueryResponse = 응답.as(new TypeRef<>() {
        });
        assertThat(restaurantQueryResponse).usingRecursiveComparison()
                .isEqualTo(예상_응답);
    }

    public static List<RestaurantQueryResponse> 예상_응답(
            List<RestaurantQueryResponse> 전체_음식점,
            Object 셀럽_ID,
            Object 카테고리,
            Object 음식점_이름
    ) {
        List<RestaurantQueryResponse> 예상_응답 = new ArrayList<>();
        Long celebId = (Long) 셀럽_ID;
        String category = (String) 카테고리;
        String restaurantName = (String) 음식점_이름;
        for (RestaurantQueryResponse restaurantQueryResponse : 전체_음식점) {
            List<Long> list = restaurantQueryResponse.celebs()
                    .stream()
                    .map(CelebQueryResponse::id)
                    .toList();

            if (음식점_이름_조건(restaurantName, restaurantQueryResponse)
                    && 카테고리_조건(category, restaurantQueryResponse)
                    && 셀럽_조건(celebId, list)) {
                예상_응답.add(restaurantQueryResponse);
            }
        }
        return 예상_응답;
    }

    private static boolean 음식점_이름_조건(String restaurantName, RestaurantQueryResponse restaurantQueryResponse) {
        if (restaurantName == null) {
            return true;
        }
        return restaurantQueryResponse.name().contains(StringUtil.removeAllBlank(restaurantName));
    }

    private static boolean 카테고리_조건(String category, RestaurantQueryResponse restaurantQueryResponse) {
        if (category == null) {
            return true;
        }
        return restaurantQueryResponse.category().equals(category);
    }

    private static boolean 셀럽_조건(Long celebId, List<Long> list) {
        if (celebId == null) {
            return true;
        }
        return list.contains(celebId);
    }

}
