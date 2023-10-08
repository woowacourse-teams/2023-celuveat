package com.celuveat.acceptance.auth;

import static com.celuveat.acceptance.auth.MemberAcceptanceSteps.회원정보_조회를_요청한다;
import static com.celuveat.acceptance.auth.OauthAcceptanceSteps.로그아웃_요청을_보낸다;
import static com.celuveat.acceptance.auth.OauthAcceptanceSteps.예상_응답;
import static com.celuveat.acceptance.auth.OauthAcceptanceSteps.응답을_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.인증되지_않음;
import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.auth.fixture.OauthMemberFixture.오도;

import com.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회원 인수테스트")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원정보를_조회한다() {
        // given
        var 오도 = 오도();
        var 세션_아이디 = 회원가입과_로그인후_세션아이디를_가져온다(오도);

        // when
        var 응답 = 회원정보_조회를_요청한다(세션_아이디);

        // then
        응답을_검증한다(응답, 예상_응답(오도));
        응답_상태를_검증한다(응답, 정상_처리);
    }

    @Test
    void 세션이_무효화된_상태에서_회원정보_조회시_401_에러가_발생한다() {
        // given
        var 오도 = 오도();
        var 세션_아이디 = 회원가입과_로그인후_세션아이디를_가져온다(오도);
        로그아웃_요청을_보낸다(세션_아이디, 오도.oauthId().oauthServer().name());

        // when
        var 응답 = 회원정보_조회를_요청한다(세션_아이디);

        // then
        응답_상태를_검증한다(응답, 인증되지_않음);
    }
}
