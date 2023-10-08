package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.값이_존재한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.없음;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_조건_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.조회_결과를_순서_상관없이_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.예상_응답;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.음식점들에_좋아요를_누른다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요_누를_음식점_아이디를_뽑는다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요_포함된_예상_응답;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요한_음식점_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요한_음식점_조회_요청_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.회원으로_음식점_검색_요청;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.LocationFixture.검색_영역_요청;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1_2번_지점포함_요청;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("음식점 좋아요 인수테스트")
public class RestaurantLikeAcceptanceTest extends AcceptanceTest {

    @Test
    void 음식점_좋아요를_누른다() {
        // given
        var 맛집 = 음식점("맛집");
        음식점을_저장한다(맛집);
        var 오도 = 멤버("오도");
        var 세션_아이디 = 회원가입과_로그인후_세션아이디를_가져온다(오도);

        // when
        var 좋아요_응답 = 좋아요_요청을_보낸다(맛집.id(), 세션_아이디);
        var 좋아요 = 음식점_좋아요를_조회한다(맛집, 오도);

        // then
        응답_상태를_검증한다(좋아요_응답, 정상_처리);
        값이_존재한다(좋아요);
    }

    @Test
    void 좋아요한_음식점을_조회한다() {
        // given
        var 전체_음식점 = seedData.insertSeedData();
        var 멤버 = 멤버("오도");
        var 세션_아이디 = 회원가입과_로그인후_세션아이디를_가져온다(멤버);

        var 좋아요_누를_음식점_아이디 = 좋아요_누를_음식점_아이디를_뽑는다(전체_음식점);
        음식점들에_좋아요를_누른다(좋아요_누를_음식점_아이디, 세션_아이디);

        // when
        var 응답 = 좋아요한_음식점_조회_요청(세션_아이디);

        // then
        var 예상_응답 = 예상_응답(전체_음식점);
        응답_상태를_검증한다(응답, 정상_처리);
        좋아요한_음식점_조회_요청_결과를_검증한다(응답, 예상_응답);
    }

    @Test
    void 로그인_상태에서_음식점을_조회하면_좋아요한_음식점의_좋아요_여부에_참값이_반환된다() {
        // given
        var 전체_음식점 = seedData.insertSeedData();
        var 멤버 = 멤버("오도");
        var 세션_아이디 = 회원가입과_로그인후_세션아이디를_가져온다(멤버);

        var 좋아요_누를_음식점_아이디 = 좋아요_누를_음식점_아이디를_뽑는다(전체_음식점);
        음식점들에_좋아요를_누른다(좋아요_누를_음식점_아이디, 세션_아이디);

        // when
        var 응답 = 회원으로_음식점_검색_요청(음식점_검색_조건_요청(없음, 없음, 없음), 검색_영역_요청(박스_1_2번_지점포함_요청), 세션_아이디);

        // then
        var 예상_응답 = 좋아요_포함된_예상_응답(전체_음식점);
        응답_상태를_검증한다(응답, 정상_처리);
        조회_결과를_순서_상관없이_검증한다(예상_응답, 응답);
    }

    private Optional<RestaurantLike> 음식점_좋아요를_조회한다(Restaurant 음식점, OauthMember 멤버) {
        return restaurantLikeRepository.findByRestaurantAndMember(음식점, 멤버);
    }
}
