package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.권한_없음;
import static com.celuveat.acceptance.common.AcceptanceSteps.내용_없음;
import static com.celuveat.acceptance.common.AcceptanceSteps.생성됨;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.리뷰_삭제_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.리뷰_수정_요청;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.리뷰_수정_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.리뷰_요청;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.리뷰_작성_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.리뷰_조회_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.예상_응답;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.응답을_검증한다;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantReviewFixture.음식점_리뷰;

import com.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("음식점 리뷰 인수테스트")
public class RestaurantReviewAcceptanceTest extends AcceptanceTest {

    @Test
    void 음식점_리뷰를_작성한다() {
        // given
        var 음식점 = 음식점("오도음식점");
        음식점을_저장한다(음식점);
        var 오도 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인한다(오도);
        var 요청 = 리뷰_요청("맛집이네요 또 올 것 같습니다", 음식점.id());

        // when
        var 응답 = 리뷰_작성_요청을_보낸다(요청, 세션_아이디);

        // then
        응답_상태를_검증한다(응답, 생성됨);
    }

    @Test
    void 음식점_리뷰를_조회한다() {
        // given
        var 음식점 = 음식점("오도음식점");
        음식점을_저장한다(음식점);

        var 도기 = 멤버("도기");
        var 도기_세션_아이디 = 회원가입하고_로그인한다(도기);
        var 로이스 = 멤버("로이스");
        var 로이스_세션_아이디 = 회원가입하고_로그인한다(로이스);
        var 말랑 = 멤버("말랑");
        var 말랑_세션_아이디 = 회원가입하고_로그인한다(말랑);

        var 요청1 = 리뷰_요청("리뷰1", 음식점.id());
        var 요청2 = 리뷰_요청("리뷰2", 음식점.id());
        var 요청3 = 리뷰_요청("리뷰3", 음식점.id());
        var 요청4 = 리뷰_요청("리뷰4", 음식점.id());
        리뷰_작성_요청을_보낸다(요청1, 도기_세션_아이디);
        리뷰_작성_요청을_보낸다(요청2, 로이스_세션_아이디);
        리뷰_작성_요청을_보낸다(요청3, 말랑_세션_아이디);
        리뷰_작성_요청을_보낸다(요청4, 말랑_세션_아이디);

        var 예상_응답 = 예상_응답();

        // when
        var 응답 = 리뷰_조회_요청을_보낸다(음식점.id());

        // then
        응답_상태를_검증한다(응답, 정상_처리);
        응답을_검증한다(응답, 예상_응답);
    }

    @Test
    void 다른사람의_음식점_리뷰를_수정한다() {
        // given
        var 음식점 = 음식점("오도음식점");
        음식점을_저장한다(음식점);
        var 오도 = 멤버("오도");
        멤버를_저장한다(오도);
        var 도기 = 멤버("도기");
        var 도기_세션_아이디 = 회원가입하고_로그인한다(도기);
        var 음식점_리뷰 = 음식점_리뷰(오도, 음식점);
        var 음식점_리뷰_아이디 = 음식점_리뷰를_저장한다(음식점_리뷰);
        var 리뷰_수정_요청 = 리뷰_수정_요청("리뷰 수정 요청");

        // when
        var 응답 = 리뷰_수정_요청을_보낸다(리뷰_수정_요청, 도기_세션_아이디, 음식점_리뷰_아이디);

        // then
        응답_상태를_검증한다(응답, 권한_없음);
    }

    @Test
    void 음식점_리뷰를_수정한다() {
        // given
        var 음식점 = 음식점("오도음식점");
        음식점을_저장한다(음식점);
        var 오도 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인한다(오도);
        var 음식점_리뷰 = 음식점_리뷰(오도, 음식점);
        var 음식점_리뷰_아이디 = 음식점_리뷰를_저장한다(음식점_리뷰);
        var 리뷰_수정_요청 = 리뷰_수정_요청("리뷰 수정 요청");

        // when
        var 응답 = 리뷰_수정_요청을_보낸다(리뷰_수정_요청, 세션_아이디, 음식점_리뷰_아이디);

        // then
        응답_상태를_검증한다(응답, 내용_없음);
    }

    @Test
    void 음식점_리뷰를_삭제한다() {
        // given
        var 음식점 = 음식점("오도음식점");
        음식점을_저장한다(음식점);
        var 오도 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인한다(오도);
        var 음식점_리뷰 = 음식점_리뷰(오도, 음식점);
        var 음식점_리뷰_아이디 = 음식점_리뷰를_저장한다(음식점_리뷰);

        // when
        var 응답 = 리뷰_삭제_요청을_보낸다(세션_아이디, 음식점_리뷰_아이디);

        // then
        응답_상태를_검증한다(응답, 내용_없음);
    }
}
