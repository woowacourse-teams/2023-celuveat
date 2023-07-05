package com.celuveat.celuveat.video;

import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.celuveat.video.VideoExceptionType.NOT_FOUND_VIDEO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.celuveat.celeb.CelebDao;
import com.celuveat.celuveat.common.annotation.DaoTest;
import com.celuveat.celuveat.common.exception.BaseExceptionType;
import com.celuveat.celuveat.restaurant.RestaurantDao;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DaoTest
@DisplayName("VideoDao 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class VideoDaoTest {

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private CelebDao celebDao;

    @Test
    void 영상을_저장한다() {
        // given
        Long 히밥_id = celebDao.save(히밥());
        Long 맛집_id = restaurantDao.save(음식점());

        Video 영상 = Video.builder()
                .celebId(히밥_id)
                .restaurantId(맛집_id)
                .title("맛있는 음식점 다녀옴. 말랑 잘하네")
                .viewCount(3)
                .videoUrl("https://naver.com")
                .publishedDate(LocalDateTime.of(2000, 10, 4, 10, 21, 22))
                .build();

        // when
        Long savedId = videoDao.save(영상);

        // then
        assertThat(savedId).isNotNull();
    }

    @Test
    void ID로_영상을_찾는다() {
        // given
        Long 히밥_id = celebDao.save(히밥());
        Long 맛집_id = restaurantDao.save(음식점());

        Video 영상 = Video.builder()
                .celebId(히밥_id)
                .restaurantId(맛집_id)
                .title("맛있는 음식점 다녀옴. 말랑 잘하네")
                .viewCount(3)
                .videoUrl("https://naver.com")
                .publishedDate(LocalDateTime.of(2000, 10, 4, 10, 21, 22))
                .build();
        Long savedId = videoDao.save(영상);

        // when
        Video result = videoDao.getById(savedId);

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(영상);

    }

    @Test
    void 영상이_없는_경우_예외가_발생한다() {
        // when
        BaseExceptionType exceptionType = assertThrows(VideoException.class, () ->
                videoDao.getById(1L)
        ).exceptionType();

        // then
        assertThat(exceptionType).isEqualTo(NOT_FOUND_VIDEO);
    }
}
