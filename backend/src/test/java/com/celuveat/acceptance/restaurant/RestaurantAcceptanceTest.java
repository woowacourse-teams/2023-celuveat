package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.셀럽_전체_조회_요청;
import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.셀럽들만_추출_한다;
import static com.celuveat.acceptance.celeb.CelebAcceptanceSteps.특정_이름의_셀럽을_찾는다;
import static com.celuveat.acceptance.common.AcceptanceSteps.생성됨;
import static com.celuveat.acceptance.common.AcceptanceSteps.없음;
import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.잘못된_요청_예외를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.검색_영역;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.근처_음식점_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.모든_음식점에_좋아요가_눌렸는지_확인한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.비회원_음식점_좋아요_조회수_예상_응답;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.상세_조회_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.상세_조회_예상_응답;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.셀럽필터_적용시_예상_응답;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.예상_응답;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_검색_조건;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_상세_조회_실패_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_상세_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_아이디를_가져온다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_좋아요_정렬_검색_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_좋아요_조회수_예상_응답;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_회원_상세_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.정보_수정_제안_요청;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.조회_결과_좋아요순_정렬_기준을_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.조회_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.조회_결과를_순서를_포함해서_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.조회수를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.특정_거리_이내에_있는_음식점이며_기준이_되는_음식점은_포함하지_않는지_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.특정_이름의_음식점을_찾는다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.음식점들에_좋아요를_누른다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.회원으로_음식점_검색_요청;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1_2번_지점포함;
import static com.celuveat.restaurant.fixture.LocationFixture.박스_1번_지점포함;
import static com.celuveat.restaurant.fixture.RestaurantLikeFixture.음식점_좋아요;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.common.SeedData;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.query.dto.RestaurantSimpleResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("음식점 인수테스트")
public class RestaurantAcceptanceTest extends AcceptanceTest {

    @Autowired
    private SeedData seedData;

    @Nested
    class 음식점_검색 {

        @Test
        void 검색영역_조건으로_음식점을_조회한다() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 예상_응답 = 예상_응답(전체_음식점, 없음, 없음, 없음, 검색_영역(박스_1_2번_지점포함));

            // when
            var 응답 = 음식점_검색_요청(음식점_검색_조건(없음, 없음, 없음), 검색_영역(박스_1_2번_지점포함));

            // then
            조회_결과를_검증한다(예상_응답, 응답);
        }

        //FIXME
        @Test
        void 음식점_좋아요_기준_정렬() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 조회_음식점 = 특정_이름의_음식점을_찾는다(전체_음식점, "로이스2호점");
            var 조회_음식점2 = 특정_이름의_음식점을_찾는다(전체_음식점, "도기1호점");
            var 오도 = 멤버("오도");
            var 로이스 = 멤버("로이스");
            var 오도_세션_아이디 = 회원가입하고_로그인한다(오도);
            var 로이스_세션_아이디 = 회원가입하고_로그인한다(로이스);
            좋아요_요청을_보낸다(조회_음식점.id(), 오도_세션_아이디);
            좋아요_요청을_보낸다(조회_음식점.id(), 로이스_세션_아이디);
            좋아요_요청을_보낸다(조회_음식점2.id(), 로이스_세션_아이디);

            // when
            var 응답 = 음식점_좋아요_정렬_검색_요청(음식점_검색_조건(없음, 없음, 없음), 검색_영역(박스_1_2번_지점포함));

            // then
            조회_결과_좋아요순_정렬_기준을_검증한다(응답);
        }


        @Test
        void 음식점_검색_조건으로_검색한다() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 셀럽들 = 셀럽들만_추출_한다(셀럽_전체_조회_요청());
            var 말랑 = 특정_이름의_셀럽을_찾는다(셀럽들, "말랑");
            var 예상_응답 = 예상_응답(전체_음식점, 말랑.id(), 없음, "말 랑 ", 검색_영역(박스_1번_지점포함));

            // when
            var 응답 = 음식점_검색_요청(음식점_검색_조건(말랑.id(), 없음, "말 랑 "), 검색_영역(박스_1번_지점포함));

            // then
            조회_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 음식점_및_검색영역_조건으로_검색한다() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 셀럽들 = 셀럽들만_추출_한다(셀럽_전체_조회_요청());
            var 말랑 = 특정_이름의_셀럽을_찾는다(셀럽들, "말랑");
            var 예상_응답 = 예상_응답(전체_음식점, 말랑.id(), 없음, "말 랑 ", 검색_영역(박스_1번_지점포함));

            // when
            var 응답 = 음식점_검색_요청(음식점_검색_조건(말랑.id(), 없음, "말 랑 "), 검색_영역(박스_1번_지점포함));

            // then
            조회_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 검색영역이_누락되면_예외가_발생한다() {
            // when
            var 응답 = 음식점_검색_요청(음식점_검색_조건(없음, 없음, 없음), 검색_영역(없음));

            // then
            잘못된_요청_예외를_검증한다(응답);
        }

        @Test
        void 특정_음식점을_기준으로_일정_거리_내에_있는_모든_음식점을_조회한다() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 음식점 = 전체_음식점.get(0);
            Long 음식점_ID = 음식점.id();
            int 요청_거리 = 2000;

            // when
            var 요청_결과 = 근처_음식점_조회_요청(음식점_ID, 요청_거리, null);

            // then
            특정_거리_이내에_있는_음식점이며_기준이_되는_음식점은_포함하지_않는지_검증한다(요청_결과, 요청_거리, 음식점_ID);
        }

        @Test
        void 근처_음식점을_조회할때_세션이_담기면_좋아요_여부도_포함된다() {
            // given
            var 도기 = 멤버를_저장한다(멤버("도기"));
            var 세션_아이디 = 회원가입하고_로그인한다(도기);
            var 전체_음식점 = seedData.insertSeedData();
            for (var restaurantSimpleResponse : 전체_음식점) {
                var 음식점 = restaurantRepository.getById(restaurantSimpleResponse.id());
                restaurantLikeRepository.save(음식점_좋아요(음식점, 도기));
            }
            Restaurant restaurant = restaurantRepository.getById(1L);

            // when
            var 요청_결과 = 근처_음식점_조회_요청(restaurant.id(), 30000, 세션_아이디);

            // then
            모든_음식점에_좋아요가_눌렸는지_확인한다(요청_결과);
        }
    }

    @Nested
    class 정보_수정_제안 {

        @Test
        void 정보_수정_제안_요청을_보낸다() {
            // given
            var 음식점_ID = seedData.insertSeedData().get(0).id();

            // when
            var 응답 = 정보_수정_제안_요청(음식점_ID, "음식점 정보가 이상해요", "일 똑바로 하세요 셀럽잇");

            // then
            응답_상태를_검증한다(응답, 생성됨);
        }

        @Test
        void 비회원으로_음식점_전체_조회시_좋아요와_조회수를_함께_반환한다() {
            // given
            var 데이터 = 여러_사용자가_음식점_좋아요를_누르고_조회한다();
            var 예상_응답 = 비회원_음식점_좋아요_조회수_예상_응답(데이터.전체_음식점);

            // when
            var 응답 = 음식점_검색_요청(음식점_검색_조건(없음, 없음, 없음), 검색_영역(박스_1_2번_지점포함));

            // then
            조회_결과를_검증한다(예상_응답, 응답);
        }

        private TestData 여러_사용자가_음식점_좋아요를_누르고_조회한다() {
            var 전체_음식점 = seedData.insertSeedData();
            var 오도 = 멤버("오도");
            var 로이스 = 멤버("로이스");
            var 도기 = 멤버("도기");
            var 말랑 = 멤버("말랑");

            var 셀럽들 = 셀럽들만_추출_한다(셀럽_전체_조회_요청());
            var 셀럽_오도 = 특정_이름의_셀럽을_찾는다(셀럽들, "오도");

            var 오도_세션_아이디 = 회원가입하고_로그인한다(오도);
            var 로이스_세션_아이디 = 회원가입하고_로그인한다(로이스);
            var 도기_세션_아이디 = 회원가입하고_로그인한다(도기);
            var 말랑_세션_아이디 = 회원가입하고_로그인한다(말랑);

            var 말랑1호점 = 전체_음식점.get(0);
            var 말랑2호점 = 전체_음식점.get(1);
            var 도기1호점 = 전체_음식점.get(3);
            var 도기2호점 = 전체_음식점.get(4);
            var 도기3호점 = 전체_음식점.get(5);
            var 오도1호점 = 전체_음식점.get(6);
            var 로이스1호점 = 전체_음식점.get(8);
            var 로이스2호점 = 전체_음식점.get(9);

            음식점들에_좋아요를_누른다(음식점_아이디를_가져온다(말랑1호점, 도기1호점, 도기2호점, 오도1호점, 로이스1호점), 오도_세션_아이디);
            음식점들에_좋아요를_누른다(음식점_아이디를_가져온다(말랑2호점, 도기1호점, 오도1호점, 로이스1호점, 로이스2호점), 로이스_세션_아이디);
            음식점들에_좋아요를_누른다(음식점_아이디를_가져온다(도기1호점, 도기3호점, 로이스1호점), 도기_세션_아이디);
            음식점들에_좋아요를_누른다(음식점_아이디를_가져온다(말랑1호점, 말랑2호점, 도기1호점, 도기2호점, 오도1호점), 말랑_세션_아이디);

            음식점_상세페이지를_여러번_방문한다(말랑2호점, 2);
            음식점_상세페이지를_여러번_방문한다(도기1호점, 3);
            음식점_상세페이지를_여러번_방문한다(도기2호점, 4);
            음식점_상세페이지를_여러번_방문한다(도기3호점, 5);
            음식점_상세페이지를_여러번_방문한다(로이스1호점, 6);
            음식점_상세페이지를_여러번_방문한다(로이스2호점, 7);

            return new TestData(전체_음식점, 오도_세션_아이디, 셀럽_오도.id());
        }

        private void 음식점_상세페이지를_여러번_방문한다(RestaurantSimpleResponse 음식점, int 횟수) {
            for (int i = 0; i < 횟수; i++) {
                restaurantService.increaseViewCount(음식점.id());
            }
        }

        @Test
        void 회원으로_음식점_전체_조회시_좋아요와_조회수를_함께_반환한다() {
            // given
            var 데이터 = 여러_사용자가_음식점_좋아요를_누르고_조회한다();
            var 예상_응답 = 음식점_좋아요_조회수_예상_응답(데이터.전체_음식점);

            // when
            var 응답 = 회원으로_음식점_검색_요청(음식점_검색_조건(없음, 없음, 없음), 검색_영역(박스_1_2번_지점포함), 데이터.세션_아이디);

            // then
            조회_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 음식점_전체_조회시_셀럽_필터를_사용하면_해당_셀럽과_셀럽의_이미지가_첫번째로_설정된다() {
            // given
            var 데이터 = 여러_사용자가_음식점_좋아요를_누르고_조회한다();
            var 예상_응답 = 셀럽필터_적용시_예상_응답(데이터.전체_음식점);

            // when
            var 응답 = 회원으로_음식점_검색_요청(음식점_검색_조건(데이터.셀럽_아이디, 없음, 없음), 검색_영역(박스_1_2번_지점포함), 데이터.세션_아이디);

            // then
            조회_결과를_순서를_포함해서_검증한다(예상_응답, 응답);
        }

        private record TestData(
                List<RestaurantSimpleResponse> 전체_음식점,
                String 세션_아이디,
                Long 셀럽_아이디
        ) {
        }
    }

    @Nested
    class 음식점_상세_조회 {

        @Test
        void 음식점ID로_조회() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 조회_음식점 = 특정_이름의_음식점을_찾는다(전체_음식점, "로이스2호점");
            var 셀럽들 = 셀럽들만_추출_한다(셀럽_전체_조회_요청());
            var 로이스 = 특정_이름의_셀럽을_찾는다(셀럽들, "로이스");
            var 예상_응답 = 상세_조회_예상_응답(전체_음식점, 조회_음식점.id(), 로이스.id(), false);

            // when
            var 응답 = 음식점_상세_조회_요청(조회_음식점.id(), 로이스.id());

            // then
            상세_조회_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 음식점ID로_조회시_좋아요_여부_반영() {
            // given
            var 전체_음식점 = seedData.insertSeedData();
            var 조회_음식점 = 특정_이름의_음식점을_찾는다(전체_음식점, "로이스2호점");
            var 셀럽들 = 셀럽들만_추출_한다(셀럽_전체_조회_요청());
            var 로이스 = 특정_이름의_셀럽을_찾는다(셀럽들, "로이스");
            var 오도 = 멤버("오도");
            var 세션_아이디 = 회원가입하고_로그인한다(오도);
            좋아요_요청을_보낸다(조회_음식점.id(), 세션_아이디);
            var 예상_응답 = 상세_조회_예상_응답(전체_음식점, 조회_음식점.id(), 로이스.id(), true);

            // when
            var 응답 = 음식점_회원_상세_조회_요청(조회_음식점.id(), 로이스.id(), 세션_아이디);

            // then
            상세_조회_결과를_검증한다(예상_응답, 응답);
        }

        @Test
        void 셀럽ID_없이_음식점을_상세_조회하면_예외가_발생한다() {
            // when
            var 조회_음식점ID = 1L;
            var 응답 = 음식점_상세_조회_요청(조회_음식점ID, (Long) 없음);

            // then
            잘못된_요청_예외를_검증한다(응답);
        }
    }
}
