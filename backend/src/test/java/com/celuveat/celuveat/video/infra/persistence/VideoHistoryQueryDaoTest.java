package com.celuveat.celuveat.video.infra.persistence;

import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.toFindAllVideoHistoryResponse;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.영상_이력;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.infra.persistence.CelebDao;
import com.celuveat.celuveat.common.annotation.DaoTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@DaoTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayName("VideoHistoryQueryDao 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class VideoHistoryQueryDaoTest {

    @Autowired
    private VideoHistoryQueryDao videoHistoryQueryDao;

    @Autowired
    private CelebDao celebDao;

    @Autowired
    private VideoHistoryDao videoHistoryDao;

    @Test
    void 유튜브로부터_저장된_전체_비디오_이력_조회() {
        // given
        var 히밥 = 히밥();
        var 성시경 = 성시경();
        var 히밥_ID = celebDao.save(히밥);
        var 성시경_ID = celebDao.save(성시경);

        var 히밥_영상_이력 = 영상_이력(히밥_ID);
        var 성시경_영상_이력 = 영상_이력(성시경_ID);
        var 히밥_영상_이력_ID = videoHistoryDao.save(히밥_영상_이력);
        var 성시경_영상_이력_ID = videoHistoryDao.save(성시경_영상_이력);

        var expected = List.of(
                toFindAllVideoHistoryResponse(히밥_영상_이력_ID, 히밥_ID),
                toFindAllVideoHistoryResponse(성시경_영상_이력_ID, 성시경_ID)
        );

        // when
        var result = videoHistoryQueryDao.findAllVideoHistory();

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}
