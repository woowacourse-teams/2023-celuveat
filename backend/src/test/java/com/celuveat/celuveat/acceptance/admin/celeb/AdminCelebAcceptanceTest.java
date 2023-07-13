package com.celuveat.celuveat.acceptance.admin.celeb;

import static com.celuveat.celuveat.acceptance.admin.auth.AdminAuthAcceptanceSteps.관리자_로그인_요청;
import static com.celuveat.celuveat.acceptance.admin.auth.AdminAuthAcceptanceSteps.세션_ID를_추출한다;
import static com.celuveat.celuveat.acceptance.admin.celeb.AdminCelebAcceptanceSteps.등록할_셀럽_정보;
import static com.celuveat.celuveat.acceptance.admin.celeb.AdminCelebAcceptanceSteps.셀럽_등록_요청;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.생성됨;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.celuveat.acceptance.common.CommonAcceptanceSteps.인증되지_않음;
import static com.celuveat.celuveat.admin.fixture.AdminFixture.관리자_도기;

import com.celuveat.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("Admin - Celeb 기능 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class AdminCelebAcceptanceTest extends AcceptanceTest {

    @Test
    void 셀럽을_등록한다() {
        // given
        관리자를_저장한다(관리자_도기());
        var 로그인_응답 = 관리자_로그인_요청("도기", "1234");
        var 세션_ID = 세션_ID를_추출한다(로그인_응답);

        var 등록할_셀럽_정보 = 등록할_셀럽_정보("히밥",
                "@heebab",
                "YOUTUBE_CHANNEL_ID",
                1_000_000,
                "https://google.com",
                "https://image.com");

        // when
        var 응답 = 셀럽_등록_요청(세션_ID, 등록할_셀럽_정보);

        // then
        응답_상태를_검증한다(응답, 생성됨);
    }

    @Test
    void 인증정보가_없거나_잘못되었다면_오류() {
        // given
        var 등록할_셀럽_정보 = 등록할_셀럽_정보("히밥",
                "@heebab",
                "YOUTUBE_CHANNEL_ID",
                1_000_000,
                "https://google.com",
                "https://image.com");

        // when
        var 응답 = 셀럽_등록_요청("잘못된 세션_ID", 등록할_셀럽_정보);

        // then
        응답_상태를_검증한다(응답, 인증되지_않음);
    }
}
