package com.celuveat.celuveat.acceptance.video;

import static com.celuveat.celuveat.acceptance.video.VideoHistoryAcceptanceSteps.모든_영상_이력_조회;
import static com.celuveat.celuveat.acceptance.video.VideoHistoryAcceptanceSteps.조회한_모든_영상_이력을_검증한다;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.toFindAllVideoHistoryResponse;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.영상_이력;

import com.celuveat.celuveat.acceptance.common.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

@DisplayName("VideoHistory 인수테스트")
public class VideoHistoryAcceptanceTest extends AcceptanceTest {

    @Test
    void 셀럽의_새로운_유튜브_영상_데이터_조회() {
        // given
        var 히밥 = 히밥();
        var 성시경 = 성시경();
        var 히밥_ID = 셀럽을_저장한다(히밥);
        var 성시경_ID = 셀럽을_저장한다(성시경);

        var 히밥_영상_이력 = 영상_이력(히밥_ID);
        var 성시경_영상_이력 = 영상_이력(성시경_ID);
        var 히밥_영상_이력_ID = 영상_이력을_저장한다(히밥_영상_이력);
        var 성시경_영상_이력_ID = 영상_이력을_저장한다(성시경_영상_이력);

        var 예상 = List.of(
                toFindAllVideoHistoryResponse(히밥_영상_이력_ID, 히밥_ID),
                toFindAllVideoHistoryResponse(성시경_영상_이력_ID, 성시경_ID)
        );

        // when
        var 응답_결과 = 모든_영상_이력_조회();

        // then
        조회한_모든_영상_이력을_검증한다(예상, 응답_결과);
    }
}
