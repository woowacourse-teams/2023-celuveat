package com.celuveat.acceptance.auth;

import static com.celuveat.acceptance.auth.OauthAcceptanceSteps.로그아웃_요청을_보낸다;
import static com.celuveat.acceptance.auth.OauthAcceptanceSteps.리다이렉트_URL을_요청한다;
import static com.celuveat.acceptance.auth.OauthAcceptanceSteps.응답에_JSESSIONID_헤더가_존재한다;
import static com.celuveat.acceptance.auth.OauthAcceptanceSteps.회원_탈퇴를_한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.내용_없음;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static org.springframework.http.HttpStatus.FOUND;

import com.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("OAuth 인수테스트")
public class OauthAcceptanceTest extends AcceptanceTest {

    @Test
    void OauthServer에_따라_미리_지정된_URI로_리다이렉트한다() {
        // given
        String oauthServerType = "KAKAO";

        // when
        var 응답 = 리다이렉트_URL을_요청한다(oauthServerType);

        // then
        응답_상태를_검증한다(응답, FOUND);
    }

    @Test
    void 로그인을_하면_세션_아이디를_헤더에_내려준다() {
        // given
        var 오도 = 멤버("오도");

        // when
        var 로그인_응답 = 회원가입하고_로그인한다(오도);

        // then
        응답에_JSESSIONID_헤더가_존재한다(로그인_응답);
    }

    @Test
    void 로그아웃한다() {
        // given
        var 오도 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인하고_세션아이디를_가져온다(오도);

        // when
        var 응답 = 로그아웃_요청을_보낸다(세션_아이디, "kakao");

        // then
        응답_상태를_검증한다(응답, 내용_없음);
    }

    @Test
    void 회원을_탈퇴한다() {
        // given
        var 도기 = 멤버("도기");
        var 세션_아이디 = 회원가입하고_로그인하고_세션아이디를_가져온다(도기);

        // when
        var 회원탈퇴_응답 = 회원_탈퇴를_한다(세션_아이디, "kakao");

        // then
        응답_상태를_검증한다(회원탈퇴_응답, 내용_없음);
    }
}
