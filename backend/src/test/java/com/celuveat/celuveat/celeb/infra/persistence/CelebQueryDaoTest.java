package com.celuveat.celuveat.celeb.infra.persistence;

import static com.celuveat.celuveat.celeb.exception.CelebExceptionType.NOT_FOUND_CELEB;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.toFindCelebByIdResponse;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.교촌치킨;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.맥도날드;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.미스터피자;
import static com.celuveat.celuveat.video.fixture.VideoFixture.영상;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.celuveat.celeb.application.dto.FindAllCelebResponse;
import com.celuveat.celuveat.celeb.application.dto.FindCelebByIdResponse;
import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.celeb.exception.CelebException;
import com.celuveat.celuveat.common.annotation.DaoTest;
import com.celuveat.celuveat.common.exception.BaseExceptionType;
import com.celuveat.celuveat.restaurant.infra.persistence.RestaurantDao;
import com.celuveat.celuveat.video.infra.persistence.VideoDao;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DaoTest
@DisplayName("CelebQueryDao 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class CelebQueryDaoTest {

    @Autowired
    private CelebQueryDao celebQueryDao;

    @Autowired
    private CelebDao celebDao;

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private VideoDao videoDao;

    private Celeb 히밥;
    private Celeb 성시경;

    @BeforeEach
    void setUp() {
        Long 히밥_ID = celebDao.save(히밥());
        Long 성시경_ID = celebDao.save(성시경());
        히밥 = 히밥(히밥_ID);
        성시경 = 성시경(성시경_ID);
    }

    @Nested
    class ID_로_셀럽_조회_시 {

        @Test
        void 셀럽을_조회한다() {
            // given
            Long 교촌_ID = restaurantDao.save(교촌치킨());
            Long 미스터피자_ID = restaurantDao.save(미스터피자());
            Long 맥도날드_ID = restaurantDao.save(맥도날드());
            videoDao.save(영상(히밥.id(), 교촌_ID));
            videoDao.save(영상(히밥.id(), 미스터피자_ID));
            videoDao.save(영상(성시경.id(), 맥도날드_ID));
            FindCelebByIdResponse expected = toFindCelebByIdResponse(히밥, 2);

            // when
            FindCelebByIdResponse response = celebQueryDao.getById(히밥.id());

            // then
            assertThat(response).isEqualTo(expected);
        }

        @Test
        void 셀럽이_없으면_예외이다() {
            // when
            BaseExceptionType baseExceptionType = assertThrows(CelebException.class, () ->
                    celebQueryDao.getById(100L)
            ).exceptionType();

            // then
            assertThat(baseExceptionType).isEqualTo(NOT_FOUND_CELEB);
        }
    }

    @Test
    void 모든_셀럽들을_조회한다() {
        // given
        List<FindAllCelebResponse> expected = List.of(
                findAllCelebResponse(히밥),
                findAllCelebResponse(성시경)
        );

        // when
        List<FindAllCelebResponse> result = celebQueryDao.findAll();

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    private static FindAllCelebResponse findAllCelebResponse(Celeb celeb) {
        return new FindAllCelebResponse(
                celeb.id(),
                celeb.name(),
                celeb.youtubeChannelName(),
                celeb.subscriberCount(),
                celeb.profileImageUrl()
        );
    }
}
