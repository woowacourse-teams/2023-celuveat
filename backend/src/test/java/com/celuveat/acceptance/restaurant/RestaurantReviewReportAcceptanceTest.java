package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.ID를_추출한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.리뷰_요청;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.리뷰_작성_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantReviewReportAcceptanceSteps.신고_요청을_보낸다;
import static com.celuveat.auth.fixture.OauthMemberFixture.도기;
import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celeb.fixture.CelebFixture.회사랑;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static com.celuveat.restaurant.fixture.RestaurantFixture.하늘초밥;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("음식점 리뷰 신고 인수테스트")
public class RestaurantReviewReportAcceptanceTest extends AcceptanceTest {

    private final OauthMember 말랑 = 말랑();
    private final OauthMember 도기 = 도기();
    private final Restaurant 대성집 = 대성집();
    private final Restaurant 하늘초밥 = 하늘초밥();
    private final Celeb 성시경 = 성시경();
    private final Celeb 회사랑 = 회사랑();

    @BeforeEach
    protected void setUp() {
        super.setUp();  // connection refused 에러 해결
        testData.addMembers(말랑, 도기);
        testData.addCelebs(성시경, 회사랑);
        testData.addRestaurants(대성집, 하늘초밥);
        초기_데이터_저장();
    }

    @Test
    void 음식점_리뷰를_신고한다() {
        // given
        var 말랑_세션_아이디 = 로그인후_세션아이디를_가져온다(말랑);
        var 도기_세션_아이디 = 로그인후_세션아이디를_가져온다(도기);
        var 요청 = 리뷰_요청("맛집이네요 또 올 것 같습니다", 대성집.id(), 5.0);
        var 말랑_리뷰_ID = ID를_추출한다(리뷰_작성_요청을_보낸다(말랑_세션_아이디, 요청));

        // when
        var 응답 = 신고_요청을_보낸다(도기_세션_아이디, 말랑_리뷰_ID, "부적절한 댓글입니다");

        // then
        응답_상태를_검증한다(응답, 정상_처리);
    }
}
