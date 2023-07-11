package com.celuveat.celuveat.video.application;

import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.video.fixture.VideoFixture.toFindAllVideoByRestaurantIdResponse;
import static com.celuveat.celuveat.video.fixture.VideoFixture.영상;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.celeb.infra.persistence.CelebDao;
import com.celuveat.celuveat.celeb.infra.persistence.FakeCelebDao;
import com.celuveat.celuveat.video.application.dto.FindAllVideoByRestaurantIdResponse;
import com.celuveat.celuveat.video.domain.Video;
import com.celuveat.celuveat.video.infra.persistence.FakeVideoQueryDao;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("VideoQueryService 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class VideoQueryServiceTest {

    private final CelebDao celebDao = new FakeCelebDao();
    private final FakeVideoQueryDao videoQueryDao = new FakeVideoQueryDao(celebDao);
    private final VideoQueryService videoQueryService = new VideoQueryService(videoQueryDao);

    @Test
    void 음식점_ID로_영상들을_찾는다() {
        // given
        Celeb 히밥 = 히밥();
        Celeb 성시경 = 성시경();
        Long 히밥_ID = celebDao.save(히밥);
        Long 성시경_ID = celebDao.save(성시경);
        Video 히밥_영상 = 영상(히밥_ID, 1L);
        Video 성시경_영상 = 영상(성시경_ID, 1L);
        videoQueryDao.save(히밥_영상);
        videoQueryDao.save(성시경_영상);
        List<FindAllVideoByRestaurantIdResponse> expected = List.of(
                toFindAllVideoByRestaurantIdResponse(히밥_영상, 히밥),
                toFindAllVideoByRestaurantIdResponse(성시경_영상, 성시경)
        );

        // when
        List<FindAllVideoByRestaurantIdResponse> result = videoQueryService.findAllByRestaurantId(1L);

        // then
        assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }
}
