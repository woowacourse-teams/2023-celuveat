package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.값이_존재한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.없음;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.검색_영역_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_조건_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.조회_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.toRestaurantLikeQueryResponse;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.음식점들에_좋아요를_누른다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요한_음식점_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요한_음식점_조회_요청_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.회원으로_음식점_검색_요청;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1_2번_지점포함_요청;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.SeedData;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("음식점 좋아요 인수테스트")
public class RestaurantLikeAcceptanceTest extends AcceptanceTest {

    @Autowired
    private SeedData seedData;

    @Test
    void 음식점_좋아요를_누른다() {
        // given
        var 맛집 = 음식점("맛집");
        음식점을_저장한다(맛집);
        var 오도 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인한다(오도);

        // when
        var 좋아요_응답 = 좋아요_요청을_보낸다(맛집.id(), 세션_아이디);
        var 좋아요 = 음식점_좋아요를_조회한다(맛집, 오도);

        // then
        응답_상태를_검증한다(좋아요_응답, 정상_처리);
        값이_존재한다(좋아요);
    }

    @Test
    void 좋아요한_음식점을_조회한다() {
        // given
        var 전체_음식점 = seedData.insertSeedData();
        var 멤버 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인한다(멤버);

        var 좋아요_누를_음식점_아이디 = 좋아요_누를_음식점_아이디를_뽑는다(전체_음식점);
        음식점들에_좋아요를_누른다(좋아요_누를_음식점_아이디, 세션_아이디);

        var 예상_응답 = 예상_응답(전체_음식점);

        // when
        var 응답 = 좋아요한_음식점_조회_요청(세션_아이디);

        // then
        응답_상태를_검증한다(응답, 정상_처리);
        좋아요한_음식점_조회_요청_결과를_검증한다(응답, 예상_응답);
    }

    @Test
    void 로그인_상태에서_음식점을_조회하면_좋아요한_음식점의_좋아요_여부에_참값이_반환된다() {
        // given
        var 전체_음식점 = seedData.insertSeedData();
        var 멤버 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인한다(멤버);

        var 좋아요_누를_음식점_아이디 = 좋아요_누를_음식점_아이디를_뽑는다(전체_음식점);
        음식점들에_좋아요를_누른다(좋아요_누를_음식점_아이디, 세션_아이디);

        var 예상_응답 = 좋아요_포함된_예상_응답(전체_음식점);

        // when
        var 응답 = 회원으로_음식점_검색_요청(음식점_검색_조건_요청(없음, 없음, 없음), 검색_영역_요청(박스_1_2번_지점포함_요청), 세션_아이디);

        // then
        응답_상태를_검증한다(응답, 정상_처리);
        조회_결과를_검증한다(예상_응답, 응답);
    }

    private List<Long> 좋아요_누를_음식점_아이디를_뽑는다(List<RestaurantSearchResponse> 전체_음식점) {
        return List.of(
                전체_음식점.get(1).getId(),
                전체_음식점.get(3).getId(),
                전체_음식점.get(4).getId(),
                전체_음식점.get(7).getId()
        );
    }

    private Optional<RestaurantLike> 음식점_좋아요를_조회한다(Restaurant 음식점, OauthMember 멤버) {
        return restaurantLikeRepository.findByRestaurantAndMember(음식점, 멤버);
    }

    private List<LikedRestaurantQueryResponse> 예상_응답(List<RestaurantSearchResponse> 전체_음식점) {
        RestaurantSearchResponse restaurantSearchResponse1 = 전체_음식점.get(1);
        RestaurantSearchResponse restaurantSearchResponse2 = 전체_음식점.get(3);
        RestaurantSearchResponse restaurantSearchResponse3 = 전체_음식점.get(4);
        RestaurantSearchResponse restaurantSearchResponse4 = 전체_음식점.get(7);
        return new ArrayList<>(List.of(
                toRestaurantLikeQueryResponse(restaurantSearchResponse4),
                toRestaurantLikeQueryResponse(restaurantSearchResponse3),
                toRestaurantLikeQueryResponse(restaurantSearchResponse2),
                toRestaurantLikeQueryResponse(restaurantSearchResponse1)
        ));
    }

    private List<RestaurantSearchResponse> 좋아요_포함된_예상_응답(
            List<RestaurantSearchResponse> 전체_음식점) {
        RestaurantSearchResponse restaurantSearchResponse1 = 전체_음식점.get(1);
        RestaurantSearchResponse restaurantSearchResponse2 = 전체_음식점.get(3);
        RestaurantSearchResponse restaurantSearchResponse3 = 전체_음식점.get(4);
        RestaurantSearchResponse restaurantSearchResponse4 = 전체_음식점.get(7);
        List<RestaurantSearchResponse> expected = new ArrayList<>(전체_음식점);
        expected.set(1, increaseLikeCount(changeIsLikedToTrue(restaurantSearchResponse1)));
        expected.set(3, increaseLikeCount(changeIsLikedToTrue(restaurantSearchResponse2)));
        expected.set(4, increaseLikeCount(changeIsLikedToTrue(restaurantSearchResponse3)));
        expected.set(7, increaseLikeCount(changeIsLikedToTrue(restaurantSearchResponse4)));
        return expected;
    }

    private RestaurantSearchResponse changeIsLikedToTrue(
            RestaurantSearchResponse restaurantSearchResponse) {
        return new RestaurantSearchResponse(
                restaurantSearchResponse.getId(),
                restaurantSearchResponse.getName(),
                restaurantSearchResponse.getCategory(),
                restaurantSearchResponse.getRoadAddress(),
                restaurantSearchResponse.getLatitude(),
                restaurantSearchResponse.getLongitude(),
                restaurantSearchResponse.getPhoneNumber(),
                restaurantSearchResponse.getNaverMapUrl(),
                restaurantSearchResponse.getViewCount(),
                restaurantSearchResponse.getDistance(),
                restaurantSearchResponse.getLikeCount(),
                true,
                restaurantSearchResponse.getCelebs(),
                restaurantSearchResponse.getImages()
        );
    }

    private RestaurantSearchResponse increaseLikeCount(
            RestaurantSearchResponse restaurantSearchResponse) {
        return new RestaurantSearchResponse(
                restaurantSearchResponse.getId(),
                restaurantSearchResponse.getName(),
                restaurantSearchResponse.getCategory(),
                restaurantSearchResponse.getRoadAddress(),
                restaurantSearchResponse.getLatitude(),
                restaurantSearchResponse.getLongitude(),
                restaurantSearchResponse.getPhoneNumber(),
                restaurantSearchResponse.getNaverMapUrl(),
                restaurantSearchResponse.getViewCount(),
                restaurantSearchResponse.getDistance(),
                restaurantSearchResponse.getLikeCount() + 1,
                restaurantSearchResponse.isLiked(),
                restaurantSearchResponse.getCelebs(),
                restaurantSearchResponse.getImages()
        );
    }
}
