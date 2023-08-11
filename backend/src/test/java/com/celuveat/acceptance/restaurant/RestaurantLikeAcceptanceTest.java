package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.값이_존재한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.없음;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.검색_영역;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_조건;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.조회_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.toRestaurantLikeQueryResponse;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.로그인을_요청한다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.음식점들에_좋아요를_누른다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요한_음식점_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요한_음식점_조회_요청_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.회원으로_음식점_검색_요청;
import static com.celuveat.auth.domain.OauthServerType.KAKAO;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1_2번_지점포함;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.auth.application.OauthService;
import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.presentation.dto.SessionResponse;
import com.celuveat.common.SeedData;
import com.celuveat.restaurant.application.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantWithCelebAndImagesSimpleResponse;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantLike;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("음식점 좋아요 인수테스트")
public class RestaurantLikeAcceptanceTest extends AcceptanceTest {

    @MockBean
    private OauthService oauthService;

    @Autowired
    private SeedData seedData;

    @Test
    void 음식점_좋아요를_누른다() {
        // given
        var 오도 = 멤버("오도");
        멤버를_저장한다(오도);
        var 맛집 = 음식점("맛집");
        음식점을_저장한다(맛집);
        OAuth_응답을_설정한다(오도);
        var 로그인_응답 = 로그인을_요청한다();
        var 세션_아이디 = 세션_아이디를_가져온다(로그인_응답);

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
        멤버를_저장한다(멤버);

        OAuth_응답을_설정한다(멤버);
        var 로그인_응답 = 로그인을_요청한다();
        var 세션_아이디 = 세션_아이디를_가져온다(로그인_응답);

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
        멤버를_저장한다(멤버);

        OAuth_응답을_설정한다(멤버);
        var 로그인_응답 = 로그인을_요청한다();
        var 세션_아이디 = 세션_아이디를_가져온다(로그인_응답);

        var 좋아요_누를_음식점_아이디 = 좋아요_누를_음식점_아이디를_뽑는다(전체_음식점);
        음식점들에_좋아요를_누른다(좋아요_누를_음식점_아이디, 세션_아이디);

        var 예상_응답 = 좋아요_포함된_예상_응답(전체_음식점);

        // when
        var 응답 = 회원으로_음식점_검색_요청(음식점_검색_조건(없음, 없음, 없음), 검색_영역(박스_1_2번_지점포함), 세션_아이디);

        // then
        응답_상태를_검증한다(응답, 정상_처리);
        조회_결과를_검증한다(예상_응답, 응답);
    }

    private List<Long> 좋아요_누를_음식점_아이디를_뽑는다(List<RestaurantWithCelebAndImagesSimpleResponse> 전체_음식점) {
        return List.of(
                전체_음식점.get(1).id(),
                전체_음식점.get(3).id(),
                전체_음식점.get(4).id(),
                전체_음식점.get(7).id()
        );
    }

    private void 음식점을_저장한다(Restaurant 음식점) {
        restaurantRepository.save(음식점);
    }

    private void OAuth_응답을_설정한다(OauthMember member) {
        Mockito.when(oauthService.login(KAKAO, "abcd")).thenReturn(member.id());
    }

    private String 세션_아이디를_가져온다(ExtractableResponse<Response> 응답) {
        return 응답.as(SessionResponse.class).jsessionId();
    }

    private Optional<RestaurantLike> 음식점_좋아요를_조회한다(Restaurant 음식점, OauthMember 멤버) {
        return restaurantLikeRepository.findByRestaurantAndMember(음식점, 멤버);
    }

    private List<RestaurantLikeQueryResponse> 예상_응답(List<RestaurantWithCelebAndImagesSimpleResponse> 전체_음식점) {
        RestaurantWithCelebAndImagesSimpleResponse restaurantWithCelebAndImagesSimpleResponse1 = 전체_음식점.get(1);
        RestaurantWithCelebAndImagesSimpleResponse restaurantWithCelebAndImagesSimpleResponse2 = 전체_음식점.get(3);
        RestaurantWithCelebAndImagesSimpleResponse restaurantWithCelebAndImagesSimpleResponse3 = 전체_음식점.get(4);
        RestaurantWithCelebAndImagesSimpleResponse restaurantWithCelebAndImagesSimpleResponse4 = 전체_음식점.get(7);
        return new ArrayList<>(List.of(
                toRestaurantLikeQueryResponse(restaurantWithCelebAndImagesSimpleResponse1),
                toRestaurantLikeQueryResponse(restaurantWithCelebAndImagesSimpleResponse2),
                toRestaurantLikeQueryResponse(restaurantWithCelebAndImagesSimpleResponse3),
                toRestaurantLikeQueryResponse(restaurantWithCelebAndImagesSimpleResponse4)
        ));
    }

    private List<RestaurantWithCelebAndImagesSimpleResponse> 좋아요_포함된_예상_응답(List<RestaurantWithCelebAndImagesSimpleResponse> 전체_음식점) {
        RestaurantWithCelebAndImagesSimpleResponse restaurantWithCelebAndImagesSimpleResponse1 = 전체_음식점.get(1);
        RestaurantWithCelebAndImagesSimpleResponse restaurantWithCelebAndImagesSimpleResponse2 = 전체_음식점.get(3);
        RestaurantWithCelebAndImagesSimpleResponse restaurantWithCelebAndImagesSimpleResponse3 = 전체_음식점.get(4);
        RestaurantWithCelebAndImagesSimpleResponse restaurantWithCelebAndImagesSimpleResponse4 = 전체_음식점.get(7);
        List<RestaurantWithCelebAndImagesSimpleResponse> expected = new ArrayList<>(전체_음식점);
        expected.set(1, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebAndImagesSimpleResponse1)));
        expected.set(3, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebAndImagesSimpleResponse2)));
        expected.set(4, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebAndImagesSimpleResponse3)));
        expected.set(7, increaseLikeCount(changeIsLikedToTrue(restaurantWithCelebAndImagesSimpleResponse4)));
        return expected;
    }

    private RestaurantWithCelebAndImagesSimpleResponse changeIsLikedToTrue(
            RestaurantWithCelebAndImagesSimpleResponse restaurantWithCelebAndImagesSimpleResponse) {
        return new RestaurantWithCelebAndImagesSimpleResponse(
                restaurantWithCelebAndImagesSimpleResponse.id(),
                restaurantWithCelebAndImagesSimpleResponse.name(),
                restaurantWithCelebAndImagesSimpleResponse.category(),
                restaurantWithCelebAndImagesSimpleResponse.roadAddress(),
                restaurantWithCelebAndImagesSimpleResponse.latitude(),
                restaurantWithCelebAndImagesSimpleResponse.longitude(),
                restaurantWithCelebAndImagesSimpleResponse.phoneNumber(),
                restaurantWithCelebAndImagesSimpleResponse.naverMapUrl(),
                restaurantWithCelebAndImagesSimpleResponse.viewCount(),
                restaurantWithCelebAndImagesSimpleResponse.distance(),
                true,
                restaurantWithCelebAndImagesSimpleResponse.likeCount(),
                restaurantWithCelebAndImagesSimpleResponse.celebs(),
                restaurantWithCelebAndImagesSimpleResponse.images()
        );
    }

    private RestaurantWithCelebAndImagesSimpleResponse increaseLikeCount(
            RestaurantWithCelebAndImagesSimpleResponse restaurantWithCelebAndImagesSimpleResponse) {
        return new RestaurantWithCelebAndImagesSimpleResponse(
                restaurantWithCelebAndImagesSimpleResponse.id(),
                restaurantWithCelebAndImagesSimpleResponse.name(),
                restaurantWithCelebAndImagesSimpleResponse.category(),
                restaurantWithCelebAndImagesSimpleResponse.roadAddress(),
                restaurantWithCelebAndImagesSimpleResponse.latitude(),
                restaurantWithCelebAndImagesSimpleResponse.longitude(),
                restaurantWithCelebAndImagesSimpleResponse.phoneNumber(),
                restaurantWithCelebAndImagesSimpleResponse.naverMapUrl(),
                restaurantWithCelebAndImagesSimpleResponse.viewCount(),
                restaurantWithCelebAndImagesSimpleResponse.distance(),
                restaurantWithCelebAndImagesSimpleResponse.isLiked(),
                restaurantWithCelebAndImagesSimpleResponse.likeCount() + 1,
                restaurantWithCelebAndImagesSimpleResponse.celebs(),
                restaurantWithCelebAndImagesSimpleResponse.images()
        );
    }
}
