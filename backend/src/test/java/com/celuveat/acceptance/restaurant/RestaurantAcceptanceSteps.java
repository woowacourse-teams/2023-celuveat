package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.PageResponse;
import com.celuveat.common.util.StringUtil;
import com.celuveat.restaurant.application.dto.CelebQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantImageQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.LocationSearchCond;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class RestaurantAcceptanceSteps {

    public static ExtractableResponse<Response> 음식점_검색_요청(
            RestaurantSearchCond 음식점_검색_조건,
            LocationSearchCond 위치_검색_조건
    ) {
        Map<String, Object> param = new HashMap<>();
        param.put("celebId", 음식점_검색_조건.celebId());
        param.put("category", 음식점_검색_조건.category());
        param.put("restaurantName", 음식점_검색_조건.restaurantName());
        param.put("lowLatitude", 위치_검색_조건.lowLatitude());
        param.put("highLatitude", 위치_검색_조건.highLatitude());
        param.put("lowLongitude", 위치_검색_조건.lowLongitude());
        param.put("highLongitude", 위치_검색_조건.highLongitude());
        return given()
                .queryParams(param)
                .when().get("/api/restaurants")
                .then().log().all()
                .extract();
    }

    public static RestaurantSearchCond 음식점_검색_조건(
            Object 셀럽_ID,
            Object 카테고리,
            Object 음식점_이름
    ) {
        return new RestaurantSearchCond(
                (Long) 셀럽_ID,
                (String) 카테고리,
                (String) 음식점_이름
        );
    }

    public static LocationSearchCond 검색_영역(Object 포함_영역) {
        if (포함_영역 == null) {
            return new LocationSearchCond(null, null, null, null);
        }
        LocationSearchCond 검색_영역 = (LocationSearchCond) 포함_영역;
        return new LocationSearchCond(
                검색_영역.lowLatitude(),
                검색_영역.highLatitude(),
                검색_영역.lowLongitude(),
                검색_영역.highLongitude()
        );
    }

    public static void 조회_결과를_검증한다(List<RestaurantQueryResponse> 예상_응답, ExtractableResponse<Response> 응답) {
        PageResponse<RestaurantQueryResponse> restaurantQueryResponse = 응답.as(new TypeRef<>() {
        });
        assertThat(restaurantQueryResponse.content())
                .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(예상_응답);
    }

    public static List<RestaurantQueryResponse> 예상_응답(
            List<RestaurantQueryResponse> 전체_음식점,
            Object 셀럽_ID,
            Object 카테고리,
            Object 음식점_이름,
            Object 검색_영역
    ) {
        List<RestaurantQueryResponse> 예상_응답 = new ArrayList<>();
        Long celebId = (Long) 셀럽_ID;
        String category = (String) 카테고리;
        String restaurantName = (String) 음식점_이름;
        LocationSearchCond locationSearchCond = (LocationSearchCond) 검색_영역;
        for (RestaurantQueryResponse restaurantQueryResponse : 전체_음식점) {
            List<Long> celebIds = restaurantQueryResponse.celebs()
                    .stream()
                    .map(CelebQueryResponse::id)
                    .toList();

            if (음식점_이름_조건(restaurantName, restaurantQueryResponse)
                    && 카테고리_조건(category, restaurantQueryResponse)
                    && 셀럽_조건(celebId, celebIds)
                    && 영역_조건(locationSearchCond, restaurantQueryResponse)) {
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

    private static boolean 영역_조건(
            LocationSearchCond locationSearchCond,
            RestaurantQueryResponse restaurantQueryResponse
    ) {
        if (locationSearchCond == null) {
            return true;
        }

        return locationSearchCond.lowLatitude() <= restaurantQueryResponse.latitude()
                && restaurantQueryResponse.latitude() <= locationSearchCond.highLatitude()
                && locationSearchCond.lowLongitude() <= restaurantQueryResponse.longitude()
                && restaurantQueryResponse.longitude() <= locationSearchCond.highLongitude();
    }

    public static RestaurantQueryResponse 특정_이름의_음식점을_찾는다(List<RestaurantQueryResponse> 음식점들, String 음식점_이름) {
        return 음식점들.stream()
                .filter(restaurantQueryResponse -> restaurantQueryResponse.name().equals(음식점_이름))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    public static ExtractableResponse<Response> 음식점_상세_조회_요청(
            Long restaurantId,
            Long celebId
    ) {
        Map<String, Object> param = new HashMap<>();
        param.put("celebId", celebId);
        return given()
                .queryParams(param)
                .when().get("/api/restaurants/{restaurantId}", restaurantId)
                .then().log().all()
                .extract();
    }

    public static void 음식점_상세_조회_실패_요청(RestaurantQueryResponse 조회_음식점) {
        음식점_상세_조회_요청(조회_음식점.id(), 12314121L);
    }

    public static RestaurantDetailQueryResponse 상세_조회_예상_응답(
            List<RestaurantQueryResponse> 전체_음식점,
            Long restaurantId,
            Long celebId
    ) {
        RestaurantQueryResponse restaurantResponse = 전체_음식점.stream()
                .filter(restaurant -> restaurant.id().equals(restaurantId))
                .findAny()
                .orElseThrow(NoSuchElementException::new);

        CelebQueryResponse targetCeleb = restaurantResponse.celebs().stream()
                .filter(celeb -> celeb.id().equals(celebId))
                .findAny()
                .orElseThrow(NoSuchElementException::new);

        return toRestaurantDetailQueryResponse(
                restaurantResponse,
                셀럽_기준으로_셀럽_정렬(targetCeleb, restaurantResponse),
                셀럽_기준으로_이미지_정렬(targetCeleb.name(), restaurantResponse));
    }

    private static List<CelebQueryResponse> 셀럽_기준으로_셀럽_정렬(
            CelebQueryResponse targetCeleb,
            RestaurantQueryResponse restaurantResponse
    ) {
        List<CelebQueryResponse> celebs = new ArrayList<>(restaurantResponse.celebs());
        Collections.swap(celebs, 0, celebs.indexOf(targetCeleb));
        return celebs;
    }

    private static List<RestaurantImageQueryResponse> 셀럽_기준으로_이미지_정렬(
            String celebName, RestaurantQueryResponse restaurantResponse
    ) {
        List<RestaurantImageQueryResponse> images = new ArrayList<>(restaurantResponse.images());
        RestaurantImageQueryResponse image = images.stream()
                .filter(targetImage -> targetImage.author().equals(celebName))
                .findFirst().orElseThrow();
        Collections.swap(images, 0, images.indexOf(image));
        return images;
    }

    private static RestaurantDetailQueryResponse toRestaurantDetailQueryResponse(
            RestaurantQueryResponse restaurantQueryResponse,
            List<CelebQueryResponse> celebs,
            List<RestaurantImageQueryResponse> images
    ) {
        return new RestaurantDetailQueryResponse(
                restaurantQueryResponse.id(),
                restaurantQueryResponse.name(),
                restaurantQueryResponse.category(),
                restaurantQueryResponse.roadAddress(),
                restaurantQueryResponse.latitude(),
                restaurantQueryResponse.longitude(),
                restaurantQueryResponse.phoneNumber(),
                restaurantQueryResponse.naverMapUrl(),
                0, // likeCount
                0, // viewCount
                celebs,
                images
        );
    }

    public static void 상세_조회_결과를_검증한다(RestaurantDetailQueryResponse 예상_응답, ExtractableResponse<Response> 응답) {
        RestaurantDetailQueryResponse response = 응답.as(new TypeRef<>() {
        });
        assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("likeCount", "viewCount")
                .isEqualTo(예상_응답);
    }

    public static void 조회수를_검증한다(int 예상_조회수, ExtractableResponse<Response> 응답) {
        RestaurantDetailQueryResponse response = 응답.as(new TypeRef<>() {
        });
        assertThat(response.viewCount()).isEqualTo(예상_조회수);
    }

    public static ExtractableResponse<Response> 근처_음식점_조회_요청(Long 특정_음식점_ID, int 요청_거리) {
        return given()
                .queryParams("distance", 요청_거리)
                .when().get("/api/restaurants/{restaurantId}/nearby", 특정_음식점_ID)
                .then().log().all()
                .extract();
    }

    public static void 특정_거리_이내에_있는_음식점이며_기준이_되는_음식점은_포함하지_않는지_검증한다(
            ExtractableResponse<Response> 요청_결과,
            int 요청_거리,
            long 기준_음식점_ID
    ) {
        PageResponse<RestaurantQueryResponse> pageResponse = 요청_결과.as(new TypeRef<>() {
        });
        assertThat(pageResponse.content())
                .isSortedAccordingTo(comparing(RestaurantQueryResponse::distance))
                .extracting(RestaurantQueryResponse::distance)
                .allMatch(distance -> distance <= 요청_거리);
        assertThat(pageResponse.content())
                .extracting(RestaurantQueryResponse::id)
                .doesNotContain(기준_음식점_ID);
    }
}
