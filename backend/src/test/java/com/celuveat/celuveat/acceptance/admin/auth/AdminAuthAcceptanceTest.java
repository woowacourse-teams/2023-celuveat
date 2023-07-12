package com.celuveat.celuveat.acceptance.admin.auth;

import static com.celuveat.celuveat.acceptance.admin.auth.AdminAuthAcceptanceSteps.관리자_로그인_요청;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.SESSION_ID_IN_COOKIE;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.값이_존재한다;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.발생한_예외를_검증한다;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.정상_처리됨;
import static com.celuveat.celuveat.admin.exception.AdminExceptionType.NOT_FOUND_ADMIN;
import static com.celuveat.celuveat.admin.exception.AdminExceptionType.NOT_MATCH_PASSWORD;
import static com.celuveat.celuveat.admin.fixture.AdminFixture.관리자_도기;
import static com.celuveat.celuveat.admin.fixture.AdminFixture.관리자_오도;

import com.celuveat.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.celuveat.admin.domain.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("Admin 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class AdminAuthAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUp() {
        Admin 도기 = 관리자_도기();
        Admin 오도 = 관리자_오도();
        관리자를_저장한다(도기);
        관리자를_저장한다(오도);
    }

    @Test
    void 존재하지_않는_아이디면_로그인에_실패한다() {
        // given
        var 존재하지_않는_아이디 = 관리자_도기().username() + "1234";

        // when
        var 응답 = 관리자_로그인_요청(존재하지_않는_아이디, 관리자_도기().password());

        // then
        발생한_예외를_검증한다(응답, NOT_FOUND_ADMIN);
    }

    @Test
    void 아이디는_존재하지만_비밀번호가_틀리면_예외가_발생한다() {
        // given
        var 존재하지_않는_비밀번호 = 관리자_도기().password() + "1234";

        // when
        var 응답 = 관리자_로그인_요청(관리자_도기().username(), 존재하지_않는_비밀번호);

        // then
        발생한_예외를_검증한다(응답, NOT_MATCH_PASSWORD);
    }

    @Test
    void 아이디와_비밀번호가_일치하면_로그인에_성공한다() {
        // when
        var 응답 = 관리자_로그인_요청(관리자_도기().username(), "1234");

        // then
        var 세션 = 응답.cookie(SESSION_ID_IN_COOKIE);
        응답_상태를_검증한다(응답, 정상_처리됨);
        값이_존재한다(세션);
    }
}
