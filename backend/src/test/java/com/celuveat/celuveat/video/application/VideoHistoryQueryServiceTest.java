package com.celuveat.celuveat.video.application;

import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.toFindAllVideoHistoryResponse;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.영상_이력;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.infra.persistence.CelebDao;
import com.celuveat.celuveat.celeb.infra.persistence.FakeCelebDao;
import com.celuveat.celuveat.video.infra.persistence.FakeVideoHistoryQueryDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("VideoHistoryQueryService 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class VideoHistoryQueryServiceTest {

    private final CelebDao celebDao = new FakeCelebDao();
    private final FakeVideoHistoryQueryDao videoHistoryQueryDao = new FakeVideoHistoryQueryDao();
    private final VideoHistoryQueryService videoHistoryQueryService = new VideoHistoryQueryService(videoHistoryQueryDao);

    @Test
    void 저장된_모든_영상_기록을_조회한다() {
        // given
        var 히밥 = 히밥();
        var 성시경 = 성시경();
        var 히밥_ID = celebDao.save(히밥);
        var 성시경_ID = celebDao.save(성시경);

        var 히밥_영상_이력 = 영상_이력(히밥_ID);
        var 성시경_영상_이력 = 영상_이력(성시경_ID);
        var 히밥_영상_이력_ID = videoHistoryQueryDao.save(히밥_영상_이력);
        var 성시경_영상_이력_ID = videoHistoryQueryDao.save(성시경_영상_이력);

        var expected = List.of(
                toFindAllVideoHistoryResponse(히밥_영상_이력_ID, 히밥_ID),
                toFindAllVideoHistoryResponse(성시경_영상_이력_ID, 성시경_ID)
        );

        // when
        var result = videoHistoryQueryService.findAllVideoHistoryResponses();

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}
