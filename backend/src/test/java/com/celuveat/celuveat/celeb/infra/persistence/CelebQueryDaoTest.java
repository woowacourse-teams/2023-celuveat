package com.celuveat.celuveat.celeb.infra.persistence;

import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.application.dto.FindAllCelebResponse;
import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.common.annotation.DaoTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
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

    @BeforeEach
    void setUp() {
        celebDao.save(히밥());
        celebDao.save(성시경());
    }

    @Test
    void 모든_셀럽들을_조회한다() {
        // given
        List<FindAllCelebResponse> expected = List.of(
                findAllCelebResponse(히밥()),
                findAllCelebResponse(성시경())
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
                celeb.youtubeId(),
                celeb.subscriberCount(),
                celeb.profileImageUrl()
        );
    }
}
