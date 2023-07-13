package com.celuveat.celuveat.acceptance.admin.auth;

import static com.celuveat.celuveat.acceptance.admin.auth.AdminAuthAcceptanceSteps.관리자_로그인_요청;
import static com.celuveat.celuveat.acceptance.admin.auth.AdminAuthAcceptanceSteps.세션_ID를_추출한다;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.값이_존재한다;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.발생한_예외를_검증한다;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.정상_처리됨;
import static com.celuveat.celuveat.admin.exception.AdminExceptionType.NOT_FOUND_ADMIN;
import static com.celuveat.celuveat.admin.exception.AdminExceptionType.NOT_MATCH_PASSWORD;
import static com.celuveat.celuveat.admin.fixture.AdminFixture.관리자_도기;

import com.celuveat.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("AdminAuth 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class AdminAuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 존재하지_않는_아이디면_로그인에_실패한다() {
        // given
        var 존재하지_않는_아이디 = "wrong username";

        // when
        var 응답 = 관리자_로그인_요청(존재하지_않는_아이디, "1234");

        // then
        발생한_예외를_검증한다(응답, NOT_FOUND_ADMIN);
    }

    @Test
    void 아이디는_존재하지만_비밀번호가_틀리면_예외가_발생한다() {
        // given
        관리자를_저장한다(관리자_도기());
        var 틀린_비밀번호 = "wrong password";

        // when
        var 응답 = 관리자_로그인_요청("도기", 틀린_비밀번호);

        // then
        발생한_예외를_검증한다(응답, NOT_MATCH_PASSWORD);
    }

    @Test
    void 아이디와_비밀번호가_일치하면_로그인에_성공한다() {
        // when
        관리자를_저장한다(관리자_도기());
        var 응답 = 관리자_로그인_요청("도기", "1234");

        // then
        응답_상태를_검증한다(응답, 정상_처리됨);
        값이_존재한다(세션_ID를_추출한다(응답));
    }
}
