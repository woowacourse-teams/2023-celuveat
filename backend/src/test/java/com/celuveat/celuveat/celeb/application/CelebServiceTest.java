package com.celuveat.celuveat.celeb.application;

import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥_저장_요청;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.application.dto.RegisterCelebCommand;
import com.celuveat.celuveat.celeb.infra.persistence.CelebDao;
import com.celuveat.celuveat.celeb.infra.persistence.FakeCelebDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("CelebService 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class CelebServiceTest {

    private final CelebDao celebDao = new FakeCelebDao();
    private final CelebService celebService = new CelebService(celebDao);

    @Test
    void 셀럽을_저장한다() {
        // given
        RegisterCelebCommand command = 히밥_저장_요청();

        // when
        Long id = celebService.register(command);

        // then
        assertThat(id).isNotNull();
    }
}
