package com.celuveat.celuveat.celeb.application;

import static com.celuveat.celuveat.celeb.fixture.CelebFixture.toFindAllCelebResponse;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.application.dto.FindAllCelebResponse;
import com.celuveat.celuveat.celeb.infra.persistence.FakeCelebQueryDao;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("CelebQueryService 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class CelebQueryServiceTest {

    private final FakeCelebQueryDao fakeCelebQueryDao = new FakeCelebQueryDao();
    private final CelebQueryService celebQueryService = new CelebQueryService(fakeCelebQueryDao);

    @BeforeEach
    void setUp() {
        fakeCelebQueryDao.save(히밥());
        fakeCelebQueryDao.save(성시경());
    }

    @Test
    void 모든_셀럽을_조회한다() {
        // given
        List<FindAllCelebResponse> expect = List.of(
                toFindAllCelebResponse(히밥()),
                toFindAllCelebResponse(성시경())
        );

        // when
        List<FindAllCelebResponse> result = celebQueryService.findAll();

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expect);
    }
}
