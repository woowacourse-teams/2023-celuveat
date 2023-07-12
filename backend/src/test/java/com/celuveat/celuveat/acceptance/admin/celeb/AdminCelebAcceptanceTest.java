package com.celuveat.celuveat.acceptance.admin.celeb;

import static com.celuveat.celuveat.acceptance.admin.celeb.AdminCelebAcceptanceSteps.등록할_셀럽_정보;
import static com.celuveat.celuveat.acceptance.admin.celeb.AdminCelebAcceptanceSteps.셀럽_등록_요청;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.생성됨;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.celuveat.admin.fixture.AdminFixture.관리자_도기;
import static com.celuveat.celuveat.admin.fixture.AdminFixture.관리자_오도;

import com.celuveat.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.celuveat.admin.domain.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("Admin - Celeb 기능 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class AdminCelebAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void setUp() {
        Admin 도기 = 관리자_도기();
        Admin 오도 = 관리자_오도();
        관리자를_저장한다(도기);
        관리자를_저장한다(오도);
    }

    @Test
    void 셀럽을_등록한다() {
        // given
        var 등록할_셀럽_정보 = 등록할_셀럽_정보("히밥",
                "@heebab",
                "YOUTUBE_CHANNEL_ID",
                1_000_000,
                "https://naver.com",
                "https://google.com",
                "https://image.com");

        // when
        var 응답 = 셀럽_등록_요청(등록할_셀럽_정보);

        // then
        응답_상태를_검증한다(응답, 생성됨);
    }
}
