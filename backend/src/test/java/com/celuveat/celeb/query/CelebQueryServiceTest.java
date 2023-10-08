package com.celuveat.celeb.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.celuveat.celeb.query.dao.FindAllCelebResponseDao;
import com.celuveat.celeb.query.dto.FindAllCelebResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("셀럽 조회 서비스(CelebQueryService) 은(는)")
class CelebQueryServiceTest {

    private final FindAllCelebResponseDao findAllCelebResponseDao = mock(FindAllCelebResponseDao.class);
    private final CelebQueryService celebQueryService = new CelebQueryService(findAllCelebResponseDao);

    @Test
    void 셀럽들을_전체_조회한다() {
        // given
        List<FindAllCelebResponse> expected = List.of(
                new FindAllCelebResponse(null, "성시경", "@성시경", "성시경 프로필 url"),
                new FindAllCelebResponse(null, "쯔양", "@쯔양", "쯔양 프로필 url"),
                new FindAllCelebResponse(null, "마리아주", "@마리아주", "마리아주 프로필 url")
        );
        given(findAllCelebResponseDao.findAll())
                .willReturn(expected);

        // when
        List<FindAllCelebResponse> actual = celebQueryService.findAll();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
