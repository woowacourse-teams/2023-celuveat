package com.celuveat.acceptance.restaurant;

import static com.celuveat.acceptance.common.AcceptanceSteps.응답_상태를_검증한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.정상_처리;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.상세_조회_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.상세_조회_응답;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.음식점_상세_조회_요청;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요_요청을_보낸다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요한_음식점_검색_결과;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요한_음식점_조회_결과를_검증한다;
import static com.celuveat.acceptance.restaurant.RestaurantLikeAcceptanceSteps.좋아요한_음식점_조회_요청;
import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celeb.fixture.CelebFixture.회사랑;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static com.celuveat.restaurant.fixture.RestaurantFixture.하늘초밥;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.대성집_사진;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.하늘초밥_사진;
import static com.celuveat.video.fixture.VideoFixture.성시경의_대성집_영상;
import static com.celuveat.video.fixture.VideoFixture.회사랑의_하늘초밥_영상;

import com.celuveat.acceptance.common.AcceptanceTest;
import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("음식점 좋아요 인수테스트")
public class RestaurantLikeAcceptanceTest extends AcceptanceTest {

    private final OauthMember 말랑 = 말랑();
    private final Restaurant 대성집 = 대성집();
    private final Restaurant 하늘초밥 = 하늘초밥();
    private final Celeb 성시경 = 성시경();
    private final Celeb 회사랑 = 회사랑();
    private final RestaurantImage 대성집_사진_1 = 대성집_사진(대성집, 1);
    private final RestaurantImage 대성집_사진_2 = 대성집_사진(대성집, 2);
    private final RestaurantImage 하늘초밥_사진_1 = 하늘초밥_사진(하늘초밥, 1);

    @BeforeEach
    protected void setUp() {
        super.setUp();  // connection refused 에러 해결
        testData.addMembers(말랑);
        testData.addCelebs(성시경, 회사랑);
        testData.addRestaurants(대성집, 하늘초밥);
        testData.addRestaurantImages(
                대성집_사진_1,
                대성집_사진_2,
                하늘초밥_사진_1
        );
        testData.addVideos(
                성시경의_대성집_영상(성시경, 대성집),
                회사랑의_하늘초밥_영상(회사랑, 하늘초밥)
        );
        초기_데이터_저장();
    }

    @Test
    void 음식점_좋아요를_누른다() {
        // given
        var 말랑_세션_ID = 로그인후_세션아이디를_가져온다(말랑);

        // when
        var 좋아요_응답 = 좋아요_요청을_보낸다(말랑_세션_ID, 대성집.id());

        // then
        응답_상태를_검증한다(좋아요_응답, 정상_처리);
        var 음식점_상세_조회_응답 = 음식점_상세_조회_요청(말랑_세션_ID, 대성집.id(), 성시경.id());
        var 예상_응답 = 상세_조회_응답(
                대성집,
                true,
                1,
                0.0,
                List.of(성시경),
                List.of(대성집_사진_1, 대성집_사진_2)
        );
        상세_조회_결과를_검증한다(예상_응답, 음식점_상세_조회_응답);
    }

    @Test
    void 좋아요한_음식점을_조회한다() {
        // given
        var 말랑_세션_아이디 = 로그인후_세션아이디를_가져온다(말랑);

        // when
        좋아요_요청을_보낸다(말랑_세션_아이디, 대성집.id());
        좋아요_요청을_보낸다(말랑_세션_아이디, 하늘초밥.id());

        // when
        var 응답 = 좋아요한_음식점_조회_요청(말랑_세션_아이디);

        // then
        응답_상태를_검증한다(응답, 정상_처리);
        // then
        var 예상_응답 = List.of(
                좋아요한_음식점_검색_결과(
                        하늘초밥,
                        1,
                        0.0,
                        List.of(회사랑),
                        List.of(하늘초밥_사진_1)
                ),
                좋아요한_음식점_검색_결과(
                        대성집,
                        1,
                        0.0,
                        List.of(성시경),
                        List.of(대성집_사진_1, 대성집_사진_2)
                )
        );
        좋아요한_음식점_조회_결과를_검증한다(예상_응답, 응답);
    }
}
