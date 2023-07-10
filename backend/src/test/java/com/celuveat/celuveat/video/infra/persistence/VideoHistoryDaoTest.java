package com.celuveat.celuveat.video.infra.persistence;

import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.video.exception.VideoHistoryExceptionType.NOT_FOUND_VIDEO_HISTORY;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.영상이력;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.celuveat.celeb.infra.persistence.CelebDao;
import com.celuveat.celuveat.common.annotation.DaoTest;
import com.celuveat.celuveat.common.exception.BaseExceptionType;
import com.celuveat.celuveat.video.domain.VideoHistory;
import com.celuveat.celuveat.video.exception.VideoHistoryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DaoTest
@DisplayName("VideoHistoryDao 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class VideoHistoryDaoTest {

    @Autowired
    private VideoHistoryDao videoHistoryDao;

    @Autowired
    private CelebDao celebDao;

    @Test
    void 영상이력을_저장한다() {
        // given
        Long 히밥_id = celebDao.save(히밥());

        VideoHistory 영상이력 = 영상이력(히밥_id);

        // when
        Long savedId = videoHistoryDao.save(영상이력);

        // then
        assertThat(savedId).isNotNull();
    }

    @Test
    void ID로_영상이력을_찾는다() {
        // given
        Long 히밥_id = celebDao.save(히밥());
        VideoHistory 영상이력 = 영상이력(히밥_id);
        Long savedId = videoHistoryDao.save(영상이력);

        // when
        VideoHistory result = videoHistoryDao.getById(savedId);

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(영상이력);
    }

    @Test
    void 영상이력이_없는_경우_예외가_발생한다() {
        // when
        BaseExceptionType exceptionType = assertThrows(VideoHistoryException.class, () ->
                videoHistoryDao.getById(1L)
        ).exceptionType();

        // then
        assertThat(exceptionType).isEqualTo(NOT_FOUND_VIDEO_HISTORY);
    }
}
