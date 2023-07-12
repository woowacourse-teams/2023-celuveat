package com.celuveat.celuveat.acceptance.video;

import static com.celuveat.celuveat.acceptance.video.VideoAcceptanceSteps.셀럽의_영상;
import static com.celuveat.celuveat.acceptance.video.VideoAcceptanceSteps.예상되는_영상_조회_응답;
import static com.celuveat.celuveat.acceptance.video.VideoAcceptanceSteps.음식점_ID로_영상_조회_요청;
import static com.celuveat.celuveat.acceptance.video.VideoAcceptanceSteps.음식점_ID로_조회한_영상을_검증한다;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.교촌치킨;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.맥도날드;
import static com.celuveat.celuveat.video.fixture.VideoFixture.영상;

import com.celuveat.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Video 인수테스트")
public class VideoAcceptanceTest extends AcceptanceTest {

    @Test
    void 음식점_ID로_영상을_조회한다() {
        // given
        var 히밥 = 히밥();
        var 성시경 = 성시경();
        var 히밥_ID = 셀럽을_저장한다(히밥);
        var 성시경_ID = 셀럽을_저장한다(성시경);

        var 맥도날드 = 맥도날드();
        var 교촌치킨 = 교촌치킨();
        var 맥도날드_ID = 음식점을_저장한다(맥도날드);
        var 교촌치킨_ID = 음식점을_저장한다(교촌치킨);

        var 히밥_영상1 = 영상(히밥_ID, 맥도날드_ID);
        var 히밥_영상2 = 영상(히밥_ID, 교촌치킨_ID);
        var 성시경_영상 = 영상(성시경_ID, 맥도날드_ID);
        영상을_저장한다(히밥_영상1);
        영상을_저장한다(히밥_영상2);
        영상을_저장한다(성시경_영상);
        var 예상_영상_조회_결과들 = 예상되는_영상_조회_응답(
                셀럽의_영상(히밥, 히밥_영상1),
                셀럽의_영상(성시경, 성시경_영상)
        );

        // when
        var 응답 = 음식점_ID로_영상_조회_요청(맥도날드_ID);

        // then
        음식점_ID로_조회한_영상을_검증한다(예상_영상_조회_결과들, 응답);
    }
}
