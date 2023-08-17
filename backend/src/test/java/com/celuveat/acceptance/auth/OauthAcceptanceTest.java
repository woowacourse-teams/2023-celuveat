package com.celuveat.acceptance.auth;

import static com.celuveat.acceptance.auth.OauthAcceptanceSteps.로그아웃_요청을_보낸다;
import static com.celuveat.acceptance.auth.OauthAcceptanceSteps.회원탈퇴_요청을_보낸다;
import static com.celuveat.acceptance.common.AcceptanceSteps.내용_없음;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;

import com.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Oauth 인수테스트")
public class OauthAcceptanceTest extends AcceptanceTest {

    @Test
    void 로그아웃한다() {
        // given
        var 오도 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인한다(오도);

        // when
        var 응답 = 로그아웃_요청을_보낸다(세션_아이디);

        // then
        응답_상태를_검증한다(응답, 내용_없음);
    }

    @Test
    void 회원탈퇴한다() {
        // given
        var 오도 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인한다(오도);

        // when
        var 응답 = 회원탈퇴_요청을_보낸다(세션_아이디);

        // then
        응답_상태를_검증한다(응답, 내용_없음);
    }
}
