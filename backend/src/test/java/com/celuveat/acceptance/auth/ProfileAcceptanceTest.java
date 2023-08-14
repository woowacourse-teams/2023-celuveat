package com.celuveat.acceptance.auth;

import static com.celuveat.acceptance.auth.ProfileAcceptanceSteps.예상_응답;
import static com.celuveat.acceptance.auth.ProfileAcceptanceSteps.응답을_검증한다;
import static com.celuveat.acceptance.auth.ProfileAcceptanceSteps.회원정보_조회를_요청한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;

import com.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("프로필 인수테스트")
public class ProfileAcceptanceTest extends AcceptanceTest {

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
}
