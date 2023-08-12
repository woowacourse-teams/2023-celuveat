package com.celuveat.acceptance.auth;

import static com.celuveat.acceptance.auth.ProfileAcceptanceSteps.예상_응답;
import static com.celuveat.acceptance.auth.ProfileAcceptanceSteps.응답을_검증한다;
import static com.celuveat.acceptance.auth.ProfileAcceptanceSteps.회원정보_조회를_요청한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.로그인을_요청한다;
import static com.celuveat.auth.domain.OauthServerType.KAKAO;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.auth.application.OauthService;
import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.presentation.dto.SessionResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("프로필 인수테스트")
public class ProfileAcceptanceTest extends AcceptanceTest {

    @MockBean
    private OauthService oauthService;

    @Test
    void 회원정보를_조회한다() {
        // given
        var 오도 = 멤버("오도");
        멤버를_저장한다(오도);
        OAuth_응답을_설정한다(오도);
        var 로그인_응답 = 로그인을_요청한다();
        var 세션_아이디 = 세션_아이디를_가져온다(로그인_응답);
        var 예상_응답 = 예상_응답(오도);

        // when
        var 응답 = 회원정보_조회를_요청한다(세션_아이디);

        // then
        응답_상태를_검증한다(응답, 정상_처리);
        응답을_검증한다(응답, 예상_응답);
    }

    private void OAuth_응답을_설정한다(OauthMember member) {
        Mockito.when(oauthService.login(KAKAO, "abcd")).thenReturn(member.id());
    }

    private String 세션_아이디를_가져온다(ExtractableResponse<Response> 응답) {
        return 응답.as(SessionResponse.class).jsessionId();
    }
}
