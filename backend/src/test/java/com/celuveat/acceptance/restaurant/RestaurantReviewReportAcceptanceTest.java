package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.acceptance.restaurant.RestaurantReviewReportAcceptanceSteps.신고_요청을_보낸다;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantReviewFixture.음식점_리뷰;

import com.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("음식점 리뷰 신고 인수테스트")
public class RestaurantReviewReportAcceptanceTest extends AcceptanceTest {

    @Test
    void 음식점_리뷰를_신고한다() {
        // given
        var 맛집 = 음식점을_저장한다(음식점("맛집"));
        var 오도 = 멤버("오도");
        회원가입하고_로그인한다(오도);
        var 리뷰_아이디 = 음식점_리뷰를_저장한다(음식점_리뷰(오도, 맛집));

        var 도기 = 멤버("도기");
        var 세션_아이디 = 회원가입하고_로그인하고_세션아이디를_가져온다(도기);
        var 신고내용 = "부적절한 댓글입니다";

        // when
        var 응답 = 신고_요청을_보낸다(신고내용, 리뷰_아이디, 세션_아이디);

        // then
        응답_상태를_검증한다(응답, 정상_처리);
    }
}
