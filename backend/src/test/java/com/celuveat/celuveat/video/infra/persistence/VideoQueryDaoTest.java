package com.celuveat.celuveat.video.infra.persistence;

import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.맥도날드;
import static com.celuveat.celuveat.video.fixture.VideoFixture.toFindAllVideoByRestaurantIdResponse;
import static com.celuveat.celuveat.video.fixture.VideoFixture.영상;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.celeb.infra.persistence.CelebDao;
import com.celuveat.celuveat.common.annotation.DaoTest;
import com.celuveat.celuveat.restaurant.infra.persistence.RestaurantDao;
import com.celuveat.celuveat.video.application.dto.FindAllVideoByRestaurantIdResponse;
import com.celuveat.celuveat.video.domain.Video;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DaoTest
@DisplayName("VideoQueryDao 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class VideoQueryDaoTest {

    @Autowired
    private VideoQueryDao videoQueryDao;

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private CelebDao celebDao;

    @Autowired
    private RestaurantDao restaurantDao;

    @Test
    void 특정_음식점_ID로_모든_영상을_검색한다() {
        // given
        Celeb 히밥 = 히밥();
        Celeb 성시경 = 성시경();
        Long 히밥_ID = celebDao.save(히밥);
        Long 성시경_ID = celebDao.save(성시경);
        Long 음식점_ID = restaurantDao.save(맥도날드());
        Video 히밥_영샹 = 영상(히밥_ID, 음식점_ID);
        Video 성시경_영상 = 영상(성시경_ID, 음식점_ID);
        videoDao.save(히밥_영샹);
        videoDao.save(성시경_영상);
        List<FindAllVideoByRestaurantIdResponse> expected = List.of(
                toFindAllVideoByRestaurantIdResponse(히밥_영샹, 히밥),
                toFindAllVideoByRestaurantIdResponse(성시경_영상, 성시경)
        );

        // when
        List<FindAllVideoByRestaurantIdResponse> result = videoQueryDao.findAllByRestaurantId(음식점_ID);

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}
