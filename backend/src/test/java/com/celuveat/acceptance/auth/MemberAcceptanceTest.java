package com.celuveat.acceptance.auth;

import static com.celuveat.acceptance.auth.MemberAcceptanceSteps.로그아웃_요청을_보낸다;
import static com.celuveat.acceptance.auth.MemberAcceptanceSteps.예상_응답;
import static com.celuveat.acceptance.auth.MemberAcceptanceSteps.응답을_검증한다;
import static com.celuveat.acceptance.auth.MemberAcceptanceSteps.회원_탈퇴를_한다;
import static com.celuveat.acceptance.auth.MemberAcceptanceSteps.회원정보_조회를_요청한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.내용_없음;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;

import com.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회원 인수테스트")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원정보를_조회한다() {
        // given
        var 오도 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인한다(오도);
        var 예상_응답 = 예상_응답(오도);

        // when
        var 응답 = 회원정보_조회를_요청한다(세션_아이디);

        // then
        응답_상태를_검증한다(응답, 정상_처리);
        응답을_검증한다(응답, 예상_응답);
    }

    @Test
    void 로그아웃한다() {
        // given
        var 오도 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인한다(오도);

        // when
        var 응답 = 로그아웃_요청을_보낸다(세션_아이디, "kakao");

        // then
        응답_상태를_검증한다(응답, 내용_없음);
    }

    @Test
    void 회원을_탈퇴한다() {
        // given
        var 도기 = 멤버("도기");
        var 세션_아이디 = 회원가입하고_로그인한다(도기);

        // when
        var 회원탈퇴_응답 = 회원_탈퇴를_한다(세션_아이디, "kakao");

        // then
        응답_상태를_검증한다(회원탈퇴_응답, 내용_없음);
    }
}
