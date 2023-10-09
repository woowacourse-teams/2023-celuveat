package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.ID를_추출한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.잘못된_요청;
import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.단일_리뷰_데이터;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.리뷰_요청;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.리뷰_작성_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.리뷰_조회_결과;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.리뷰_조회_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.음식점_리뷰_조회_응답을_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantReviewLikeAcceptanceSteps.좋아요_요청을_보낸다;
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

@DisplayName("음식점 리뷰 좋아요 인수테스트")
public class RestaurantReviewLikeAcceptanceTest extends AcceptanceTest {

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
    void 음식점_리뷰_좋아요를_누른다() {
        // given
        var 말랑_세션_아이디 = 로그인후_세션아이디를_가져온다(말랑);
        var 도기_세션_아이디 = 로그인후_세션아이디를_가져온다(도기);
        var 요청 = 리뷰_요청("맛집이네요 또 올 것 같습니다", 대성집.id(), 5.0);
        var 말랑_리뷰_ID = ID를_추출한다(리뷰_작성_요청을_보낸다(말랑_세션_아이디, 요청));

        // when
        var 응답 = 좋아요_요청을_보낸다(도기_세션_아이디, 말랑_리뷰_ID);

        // then
        응답_상태를_검증한다(응답, 정상_처리);
    }

    @Test
    void 자신의_음식점_리뷰에_좋아요를_누를수없다() {
        // given
        var 말랑_세션_아이디 = 로그인후_세션아이디를_가져온다(말랑);
        var 요청 = 리뷰_요청("맛집이네요 또 올 것 같습니다", 대성집.id(), 5.0);
        var 말랑_리뷰_ID = ID를_추출한다(리뷰_작성_요청을_보낸다(말랑_세션_아이디, 요청));

        // when
        var 응답 = 좋아요_요청을_보낸다(말랑_세션_아이디, 말랑_리뷰_ID);

        // then
        응답_상태를_검증한다(응답, 잘못된_요청);
    }

    @Test
    void 로그인후_리뷰를_조회시_좋아요_여부가_반영된다() {
        // given
        var 말랑_세션_아이디 = 로그인후_세션아이디를_가져온다(말랑);
        var 도기_세션_아이디 = 로그인후_세션아이디를_가져온다(도기);
        var 요청 = 리뷰_요청("맛집이네요 또 올 것 같습니다", 대성집.id(), 5.0);
        var 말랑_리뷰_ID = ID를_추출한다(리뷰_작성_요청을_보낸다(말랑_세션_아이디, 요청));
        좋아요_요청을_보낸다(도기_세션_아이디, 말랑_리뷰_ID);

        // when
        var 응답 = 리뷰_조회_요청을_보낸다(도기_세션_아이디, 대성집.id());

        // then
        응답_상태를_검증한다(응답, 정상_처리);
        var 예상_응답 = 리뷰_조회_결과(1,
                단일_리뷰_데이터(말랑, "맛집이네요 또 올 것 같습니다", 5.0, 1, true)
        );
        음식점_리뷰_조회_응답을_검증한다(예상_응답, 응답);
    }
}
