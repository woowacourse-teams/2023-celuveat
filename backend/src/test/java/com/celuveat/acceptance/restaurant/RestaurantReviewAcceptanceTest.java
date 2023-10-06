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
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.사진_2장이_포함된_리뷰_작성_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.예상_응답;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.응답을_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantReviewAcceptanceSteps.이미지를_생성한다;
import static com.celuveat.acceptance.restaurant.RestaurantReviewLikeAcceptanceSteps.좋아요_요청을_보낸다;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantReviewFixture.음식점_리뷰;

import com.celuveat.acceptance.common.AcceptanceTest;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

@DisplayName("음식점 리뷰 인수테스트")
public class RestaurantReviewAcceptanceTest extends AcceptanceTest {

    @Test
    void 음식점_리뷰를_작성한다() {
        // given
        var 음식점 = 음식점("오도음식점");
        음식점을_저장한다(음식점);
        var 오도 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인하고_세션아이디를_가져온다(오도);
        var 요청 = 리뷰_요청("맛집이네요 또 올 것 같습니다", 음식점.id(), 5.0);

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
        var 도기_세션_아이디 = 회원가입하고_로그인하고_세션아이디를_가져온다(도기);
        var 로이스 = 멤버("로이스");
        var 로이스_세션_아이디 = 회원가입하고_로그인하고_세션아이디를_가져온다(로이스);
        var 말랑 = 멤버("말랑");
        var 말랑_세션_아이디 = 회원가입하고_로그인하고_세션아이디를_가져온다(말랑);

        var 요청1 = 리뷰_요청("리뷰1", 음식점.id(), 5.0);
        var 요청2 = 리뷰_요청("리뷰2", 음식점.id(), 5.0);
        var 요청3 = 리뷰_요청("리뷰3", 음식점.id(), 5.0);
        var 요청4 = 리뷰_요청("리뷰4", 음식점.id(), 5.0);
        리뷰_작성_요청을_보낸다(요청1, 도기_세션_아이디);
        리뷰_작성_요청을_보낸다(요청2, 로이스_세션_아이디);
        리뷰_작성_요청을_보낸다(요청3, 말랑_세션_아이디);
        리뷰_작성_요청을_보낸다(요청4, 말랑_세션_아이디);

        var 예상_응답 = 예상_응답(말랑.id(), 로이스.id(), 도기.id());

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
        var 도기_세션_아이디 = 회원가입하고_로그인하고_세션아이디를_가져온다(도기);
        var 음식점_리뷰 = 음식점_리뷰(오도, 음식점);
        var 음식점_리뷰_아이디 = 음식점_리뷰를_저장한다(음식점_리뷰);
        var 리뷰_수정_요청 = 리뷰_수정_요청("리뷰 수정 요청", 5.0);

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
        var 세션_아이디 = 회원가입하고_로그인하고_세션아이디를_가져온다(오도);
        var 음식점_리뷰 = 음식점_리뷰(오도, 음식점);
        var 음식점_리뷰_아이디 = 음식점_리뷰를_저장한다(음식점_리뷰);
        var 리뷰_수정_요청 = 리뷰_수정_요청("리뷰 수정 요청", 5.0);

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
        var 세션_아이디 = 회원가입하고_로그인하고_세션아이디를_가져온다(오도);
        var 음식점_리뷰 = 음식점_리뷰(오도, 음식점);
        var 음식점_리뷰_아이디 = 음식점_리뷰를_저장한다(음식점_리뷰);

        // when
        var 응답 = 리뷰_삭제_요청을_보낸다(세션_아이디, 음식점_리뷰_아이디);

        // then
        응답_상태를_검증한다(응답, 내용_없음);
    }

    @Test
    void 음식점_리뷰에_사진을_첨부하여_작성한다() throws IOException {
        // given
        var 음식점 = 음식점("오도음식점");
        음식점을_저장한다(음식점);
        var 오도 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인하고_세션아이디를_가져온다(오도);
        var 리뷰_이미지 = List.of(이미지를_생성한다("images", "이미지1번.wepb"), 이미지를_생성한다("images", "이미지1번.wepb"));
        이미지_업로드를_설정한다(리뷰_이미지);
        var 요청 = 리뷰_요청("맛집이네요 또 올 것 같습니다", 음식점.id(), 5.0, 리뷰_이미지);

        // when
        var 응답 = 사진_2장이_포함된_리뷰_작성_요청을_보낸다(요청, 세션_아이디);

        // then
        응답_상태를_검증한다(응답, 생성됨);
    }

    @Test
    void 사진이_포함된_리뷰를_조회한다() throws IOException {
        // given
        var 음식점 = 음식점("오도음식점");
        음식점을_저장한다(음식점);
        var 오도 = 멤버("오도");
        var 세션_아이디 = 회원가입하고_로그인하고_세션아이디를_가져온다(오도);
        List<MultipartFile> 리뷰_이미지 = List.of(이미지를_생성한다("images", "image1.wepb"), 이미지를_생성한다("images", "image2.wepb"));
        이미지_업로드를_설정한다(리뷰_이미지);
        var 요청 = 리뷰_요청("맛집이네요 또 올 것 같습니다", 음식점.id(), 5.0, 리뷰_이미지);
        사진_2장이_포함된_리뷰_작성_요청을_보낸다(요청, 세션_아이디);

        // when
        var 응답 = 리뷰_조회_요청을_보낸다(음식점.id());

        // then
        응답_상태를_검증한다(응답, 정상_처리);
    }

    @Test
    void 로그인후_리뷰를_조회시_좋아요_여부가_반영된다() {
        // given
        var 음식점 = 음식점("오도음식점");
        음식점을_저장한다(음식점);
        var 오도 = 멤버("오도");
        회원가입하고_로그인하고_세션아이디를_가져온다(오도);
        var 리뷰_아이디 = 음식점_리뷰를_저장한다(음식점_리뷰(오도, 음식점));
        음식점_리뷰를_저장한다(음식점_리뷰(오도, 음식점));

        var 로이스 = 멤버("로이스");
        var 로이스_세션_아이디 = 회원가입하고_로그인하고_세션아이디를_가져온다(로이스);
        좋아요_요청을_보낸다(리뷰_아이디, 로이스_세션_아이디);

        // when
        var 응답 = 리뷰_조회_요청을_보낸다(음식점.id(), 로이스_세션_아이디);

        // then
        응답_상태를_검증한다(응답, 정상_처리);
    }
}
