package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.PageResponse;
import com.celuveat.common.util.StringUtil;
import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.RestaurantSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.SuggestCorrectionRequest;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.LocationSearchCond;
import com.celuveat.restaurant.query.dto.CelebQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.query.dto.RestaurantImageQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class RestaurantAcceptanceSteps {

    public static ExtractableResponse<Response> 음식점_검색_요청(
            RestaurantSearchCondRequest 음식점_검색_조건,
            LocationSearchCondRequest 위치_검색_조건
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
                .when().get("/restaurants")
                .then()
                .log().all()
                .extract();
    }

    //FIXME
    public static ExtractableResponse<Response> 음식점_좋아요_정렬_검색_요청(
            RestaurantSearchCondRequest 음식점_검색_조건,
            LocationSearchCondRequest 위치_검색_조건
    ) {
        Map<String, Object> param = new HashMap<>();
        param.put("celebId", 음식점_검색_조건.celebId());
        param.put("category", 음식점_검색_조건.category());
        param.put("restaurantName", 음식점_검색_조건.restaurantName());
        param.put("sort", "like");
        param.put("lowLatitude", 위치_검색_조건.lowLatitude());
        param.put("highLatitude", 위치_검색_조건.highLatitude());
        param.put("lowLongitude", 위치_검색_조건.lowLongitude());
        param.put("highLongitude", 위치_검색_조건.highLongitude());
        return given()
                .queryParams(param)
                .when().get("/restaurants")
                .then().log().all()
                .extract();
    }

    public static RestaurantSearchCondRequest 음식점_검색_조건_요청(
            Object 셀럽_ID,
            Object 카테고리,
            Object 음식점_이름
    ) {
        return new RestaurantSearchCondRequest(
                (Long) 셀럽_ID,
                (String) 카테고리,
                (String) 음식점_이름
        );
    }

    public static LocationSearchCond 검색_영역(Object 포함_영역) {
        LocationSearchCond 검색_영역 = (LocationSearchCond) 포함_영역;
        return new LocationSearchCond(
                검색_영역.lowLatitude(),
                검색_영역.highLatitude(),
                검색_영역.lowLongitude(),
                검색_영역.highLongitude()
        );
    }

    public static LocationSearchCondRequest 검색_영역_요청(Object 포함_영역) {
        if (포함_영역 == null) {
            return new LocationSearchCondRequest(null, null, null, null);
        }
        LocationSearchCondRequest 검색_영역 = (LocationSearchCondRequest) 포함_영역;
        return new LocationSearchCondRequest(
                검색_영역.lowLatitude(),
                검색_영역.highLatitude(),
                검색_영역.lowLongitude(),
                검색_영역.highLongitude()
        );
    }

    public static void 조회_결과를_검증한다(List<RestaurantSearchResponse> 예상_응답,
                                   ExtractableResponse<Response> 응답) {
        PageResponse<RestaurantSearchResponse> restaurantQueryResponse = 응답.as(new TypeRef<>() {
        });
        assertThat(restaurantQueryResponse.content())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .ignoringCollectionOrder()
                .isEqualTo(예상_응답);
    }

    //FIXME
    public static void 조회_결과_좋아요순_정렬_기준을_검증한다(ExtractableResponse<Response> 응답) {
        PageResponse<RestaurantSearchResponse> restaurantQueryResponse = 응답.as(new TypeRef<>() {
        });
        assertThat(restaurantQueryResponse.content())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::likeCount).reversed());
    }


    public static void 조회_결과를_순서를_포함해서_검증한다(List<RestaurantSearchResponse> 예상_응답,
                                            ExtractableResponse<Response> 응답) {
        PageResponse<RestaurantSearchResponse> restaurantQueryResponse = 응답.as(new TypeRef<>() {
        });
        assertThat(restaurantQueryResponse.content())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::distance))
                .usingRecursiveComparison()
                .ignoringFields("distance")
                .isEqualTo(예상_응답);
    }

    public static List<RestaurantSearchResponse> 예상_응답(
            List<RestaurantSearchResponse> 전체_음식점,
            Object 셀럽_ID,
            Object 카테고리,
            Object 음식점_이름,
            Object 검색_영역
    ) {
        List<RestaurantSearchResponse> 예상_응답 = new ArrayList<>();
        Long celebId = (Long) 셀럽_ID;
        String category = (String) 카테고리;
        String restaurantName = (String) 음식점_이름;
        LocationSearchCond locationSearchCond = (LocationSearchCond) 검색_영역;
        for (RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse : 전체_음식점) {
            List<Long> celebIds = restaurantWithCelebsAndImagesSimpleResponse.celebs()
                    .stream()
                    .map(CelebQueryResponse::id)
                    .toList();

            if (음식점_이름_조건(restaurantName, restaurantWithCelebsAndImagesSimpleResponse)
                    && 카테고리_조건(category, restaurantWithCelebsAndImagesSimpleResponse)
                    && 셀럽_조건(celebId, celebIds)
                    && 영역_조건(locationSearchCond, restaurantWithCelebsAndImagesSimpleResponse)) {
                예상_응답.add(restaurantWithCelebsAndImagesSimpleResponse);
            }
        }
        return 예상_응답;
    }

    public static List<RestaurantSearchResponse> 비회원_음식점_좋아요_조회수_예상_응답(
            List<RestaurantSearchResponse> 전체_음식점
    ) {
        List<RestaurantSearchResponse> expected = new ArrayList<>(전체_음식점);
        expected.set(0, createExpectedResponse(전체_음식점.get(0), 0, false, 2));
        expected.set(1, createExpectedResponse(전체_음식점.get(1), 2, false, 2));
        expected.set(3, createExpectedResponse(전체_음식점.get(3), 3, false, 4));
        expected.set(4, createExpectedResponse(전체_음식점.get(4), 4, false, 2));
        expected.set(5, createExpectedResponse(전체_음식점.get(5), 5, false, 1));
        expected.set(6, createExpectedResponse(전체_음식점.get(6), 0, false, 3));
        expected.set(8, createExpectedResponse(전체_음식점.get(8), 6, false, 3));
        expected.set(9, createExpectedResponse(전체_음식점.get(9), 7, false, 1));
        return expected;
    }

    public static List<RestaurantSearchResponse> 음식점_좋아요_조회수_예상_응답(
            List<RestaurantSearchResponse> 전체_음식점
    ) {
        List<RestaurantSearchResponse> expected = new ArrayList<>(전체_음식점);
        expected.set(0, createExpectedResponse(전체_음식점.get(0), 0, true, 2));
        expected.set(1, createExpectedResponse(전체_음식점.get(1), 2, false, 2));
        expected.set(3, createExpectedResponse(전체_음식점.get(3), 3, true, 4));
        expected.set(4, createExpectedResponse(전체_음식점.get(4), 4, true, 2));
        expected.set(5, createExpectedResponse(전체_음식점.get(5), 5, false, 1));
        expected.set(6, createExpectedResponse(전체_음식점.get(6), 0, true, 3));
        expected.set(8, createExpectedResponse(전체_음식점.get(8), 6, true, 3));
        expected.set(9, createExpectedResponse(전체_음식점.get(9), 7, false, 1));
        return expected;
    }

    private static RestaurantSearchResponse createExpectedResponse(
            RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse,
            int viewCountValue,
            boolean isLiked,
            int likeCountValue
    ) {
        return new RestaurantSearchResponse(
                restaurantWithCelebsAndImagesSimpleResponse.id(),
                restaurantWithCelebsAndImagesSimpleResponse.name(),
                restaurantWithCelebsAndImagesSimpleResponse.category(),
                restaurantWithCelebsAndImagesSimpleResponse.roadAddress(),
                restaurantWithCelebsAndImagesSimpleResponse.latitude(),
                restaurantWithCelebsAndImagesSimpleResponse.longitude(),
                restaurantWithCelebsAndImagesSimpleResponse.phoneNumber(),
                restaurantWithCelebsAndImagesSimpleResponse.naverMapUrl(),
                restaurantWithCelebsAndImagesSimpleResponse.viewCount() + viewCountValue,
                restaurantWithCelebsAndImagesSimpleResponse.distance(),
                isLiked,
                restaurantWithCelebsAndImagesSimpleResponse.likeCount() + likeCountValue,
                restaurantWithCelebsAndImagesSimpleResponse.celebs(),
                restaurantWithCelebsAndImagesSimpleResponse.images()
        );
    }

    public static List<RestaurantSearchResponse> 셀럽필터_적용시_예상_응답(
            List<RestaurantSearchResponse> 전체_음식점
    ) {
        List<RestaurantSearchResponse> expected = new ArrayList<>();
        RestaurantSearchResponse 도기1호점 = createExpectedResponse(전체_음식점.get(3), 3, true, 4);
        RestaurantSearchResponse 도기3호점 = createExpectedResponse(전체_음식점.get(5), 5, false, 1);
        RestaurantSearchResponse 오도1호점 = createExpectedResponse(전체_음식점.get(6), 0, true, 3);
        RestaurantSearchResponse 오도2호점 = createExpectedResponse(전체_음식점.get(7), 0, false, 0);
        RestaurantSearchResponse 로이스1호점 = createExpectedResponse(전체_음식점.get(8), 6, true, 3);

        expected.add(changeOrder(도기1호점, 0, 1, 0, 1));
        expected.add(changeOrder(오도1호점, 0, 0, 0, 2));
        expected.add(changeOrder(로이스1호점, 0, 2, 0, 0));
        expected.add(changeOrder(도기3호점, 0, 1, 0, 1));
        expected.add(changeOrder(오도2호점, 0, 0, 0, 0));
        return expected;
    }

    public static RestaurantSearchResponse changeOrder(
            RestaurantSearchResponse response,
            int celebIndex1,
            int celebIndex2,
            int imageIndex1,
            int imageIndex2
    ) {
        List<CelebQueryResponse> celebs = new ArrayList<>(response.celebs());
        List<RestaurantImageQueryResponse> images = new ArrayList<>(response.images());
        Collections.swap(celebs, celebIndex1, celebIndex2);
        Collections.swap(images, imageIndex1, imageIndex2);
        return RestaurantSearchResponse.of(response, celebs, images);
    }

    public static List<Long> 음식점_아이디를_가져온다(RestaurantSearchResponse... 음식점들) {
        return Arrays.stream(음식점들)
                .map(RestaurantSearchResponse::id)
                .toList();
    }

    private static boolean 음식점_이름_조건(String restaurantName,
                                     RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse) {
        if (restaurantName == null) {
            return true;
        }
        return restaurantWithCelebsAndImagesSimpleResponse.name().contains(StringUtil.removeAllBlank(restaurantName));
    }

    private static boolean 카테고리_조건(String category,
                                   RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse) {
        if (category == null) {
            return true;
        }
        return restaurantWithCelebsAndImagesSimpleResponse.category().equals(category);
    }

    private static boolean 셀럽_조건(Long celebId, List<Long> list) {
        if (celebId == null) {
            return true;
        }
        return list.contains(celebId);
    }

    private static boolean 영역_조건(
            LocationSearchCond locationSearchCond,
            RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse
    ) {
        if (locationSearchCond == null) {
            return true;
        }

        return locationSearchCond.lowLatitude() <= restaurantWithCelebsAndImagesSimpleResponse.latitude()
                && restaurantWithCelebsAndImagesSimpleResponse.latitude() <= locationSearchCond.highLatitude()
                && locationSearchCond.lowLongitude() <= restaurantWithCelebsAndImagesSimpleResponse.longitude()
                && restaurantWithCelebsAndImagesSimpleResponse.longitude() <= locationSearchCond.highLongitude();
    }

    public static RestaurantSearchResponse 특정_이름의_음식점을_찾는다(
            List<RestaurantSearchResponse> 음식점들, String 음식점_이름) {
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
                .when().get("/restaurants/{restaurantId}", restaurantId)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 음식점_회원_상세_조회_요청(
            Long restaurantId,
            Long celebId,
            String sessionId
    ) {
        Map<String, Object> param = new HashMap<>();
        param.put("celebId", celebId);
        return given(sessionId)
                .queryParams(param)
                .when().get("/restaurants/{restaurantId}", restaurantId)
                .then()
                .extract();
    }

    public static void 음식점_상세_조회_실패_요청(RestaurantSearchResponse 조회_음식점) {
        음식점_상세_조회_요청(조회_음식점.id(), 12314121L);
    }

    public static RestaurantDetailResponse 상세_조회_예상_응답(
            List<RestaurantSearchResponse> 전체_음식점,
            Long restaurantId,
            Long celebId,
            boolean isLiked
    ) {
        RestaurantSearchResponse restaurantResponse = 전체_음식점.stream()
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
                셀럽_기준으로_이미지_정렬(targetCeleb.name(), restaurantResponse),
                isLiked
        );
    }

    private static List<CelebQueryResponse> 셀럽_기준으로_셀럽_정렬(
            CelebQueryResponse targetCeleb,
            RestaurantSearchResponse restaurantResponse
    ) {
        List<CelebQueryResponse> celebs = new ArrayList<>(restaurantResponse.celebs());
        Collections.swap(celebs, 0, celebs.indexOf(targetCeleb));
        return celebs;
    }

    private static List<RestaurantImageQueryResponse> 셀럽_기준으로_이미지_정렬(
            String celebName, RestaurantSearchResponse restaurantResponse
    ) {
        List<RestaurantImageQueryResponse> images = new ArrayList<>(restaurantResponse.images());
        RestaurantImageQueryResponse image = images.stream()
                .filter(targetImage -> targetImage.author().equals(celebName))
                .findFirst().orElseThrow();
        Collections.swap(images, 0, images.indexOf(image));
        return images;
    }

    private static RestaurantDetailResponse toRestaurantDetailQueryResponse(
            RestaurantSearchResponse restaurantWithCelebsAndImagesSimpleResponse,
            List<CelebQueryResponse> celebs,
            List<RestaurantImageQueryResponse> images,
            boolean isLiked
    ) {
        return new RestaurantDetailResponse(
                restaurantWithCelebsAndImagesSimpleResponse.id(),
                restaurantWithCelebsAndImagesSimpleResponse.name(),
                restaurantWithCelebsAndImagesSimpleResponse.category(),
                restaurantWithCelebsAndImagesSimpleResponse.roadAddress(),
                restaurantWithCelebsAndImagesSimpleResponse.latitude(),
                restaurantWithCelebsAndImagesSimpleResponse.longitude(),
                restaurantWithCelebsAndImagesSimpleResponse.phoneNumber(),
                restaurantWithCelebsAndImagesSimpleResponse.naverMapUrl(),
                0, // likeCount
                0, // viewCount
                isLiked,
                celebs,
                images
        );
    }

    public static void 상세_조회_결과를_검증한다(RestaurantDetailResponse 예상_응답,
                                      ExtractableResponse<Response> 응답) {
        RestaurantDetailResponse response = 응답.as(new TypeRef<>() {
        });
        assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("likeCount", "viewCount")
                .isEqualTo(예상_응답);
    }

    public static void 조회수를_검증한다(int 예상_조회수, ExtractableResponse<Response> 응답) {
        RestaurantDetailResponse response = 응답.as(new TypeRef<>() {
        });
        assertThat(response.viewCount()).isEqualTo(예상_조회수);
    }

    public static ExtractableResponse<Response> 정보_수정_제안_요청(Long 음식점_ID, String... 수정_내용들) {
        return given()
                .body(new SuggestCorrectionRequest(Arrays.asList(수정_내용들)))
                .post("/restaurants/{id}/correction", 음식점_ID)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 근처_음식점_조회_요청(Long 특정_음식점_ID, int 요청_거리, String 세션_아이디) {
        return given(세션_아이디)
                .queryParams("distance", 요청_거리)
                .when().get("/restaurants/{restaurantId}/nearby", 특정_음식점_ID)
                .then().log().all()
                .extract();
    }

    public static void 특정_거리_이내에_있는_음식점이며_기준이_되는_음식점은_포함하지_않는지_검증한다(
            ExtractableResponse<Response> 요청_결과,
            int 요청_거리,
            long 기준_음식점_ID
    ) {
        PageResponse<RestaurantSearchResponse> pageResponse = 요청_결과.as(new TypeRef<>() {
        });
        assertThat(pageResponse.content())
                .isSortedAccordingTo(comparing(RestaurantSearchResponse::distance))
                .extracting(RestaurantSearchResponse::distance)
                .allMatch(distance -> distance <= 요청_거리);
        assertThat(pageResponse.content())
                .extracting(RestaurantSearchResponse::id)
                .doesNotContain(기준_음식점_ID);
    }

    public static void 모든_음식점에_좋아요가_눌렸는지_확인한다(ExtractableResponse<Response> 요청_결과) {
        PageResponse<RestaurantSearchResponse> pageResponse = 요청_결과.as(new TypeRef<>() {
        });
        assertThat(pageResponse.content()).extracting(RestaurantSearchResponse::isLiked)
                .allMatch(isLiked -> isLiked);
    }
}
