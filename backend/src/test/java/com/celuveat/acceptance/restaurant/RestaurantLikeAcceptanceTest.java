package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.로그인을_요청한다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.예상_응답;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요한_음식점_조회_요청;
import static com.celuveat.auth.domain.OauthServerType.KAKAO;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.auth.application.OauthService;
import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.presentation.SessionResponse;
import com.celuveat.common.SeedData;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantLike;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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
        var 좋아요_응답 = 좋아요_요청을_보낸다(맛집, 세션_아이디);
        var 결과 = 음식점_좋아요를_조회한다(맛집, 오도);

        // then
        응답_상태를_검증한다(좋아요_응답, 정상_처리);
        결과를_검증한다(결과);
    }

    @Test
    void 좋아요한_음식점을_조회한다() {
        // given
        var 데이터_입력_결과 = seedData.insertSeedData();
        var 멤버 = 데이터_입력_결과.member();
        var 전체_음식점 = 데이터_입력_결과.restaurants();
        OAuth_응답을_설정한다(멤버);
        var 로그인_응답 = 로그인을_요청한다();
        var 세션_아이디 = 세션_아이디를_가져온다(로그인_응답);
        var 예상_응답 = 예상_응답(전체_음식점);

        // when
        var 응답 = 좋아요한_음식점_조회_요청(세션_아이디);

        // then
        응답_상태를_검증한다(응답, 정상_처리);
        결과를_검증한다(응답, 예상_응답);
    }

    private void 멤버를_저장한다(OauthMember 멤버) {
        oauthMemberRepository.save(멤버);
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
}
