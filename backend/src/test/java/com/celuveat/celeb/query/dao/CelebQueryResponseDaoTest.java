package com.celuveat.celeb.query.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.celeb.query.dto.CelebQueryResponse;
import com.celuveat.common.DaoTest;
import com.celuveat.common.TestData;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("셀럽 조회 DAO(CelebQueryResponseDao) 은(는)")
class CelebQueryResponseDaoTest extends DaoTest {

    @Autowired
    private CelebQueryResponseDao celebQueryResponseDao;

    @Override
    protected TestData prepareTestData() {
        Celeb 성시경 = new Celeb("성시경", "@성시경", "성시경 프로필 url");
        Celeb 쯔양 = new Celeb("쯔양", "@쯔양", "쯔양 프로필 url");
        Celeb 마리아주 = new Celeb("마리아주", "@마리아주", "마리아주 프로필 url");
        testData.addCelebs(List.of(성시경, 쯔양, 마리아주));
        return testData;
    }

    @Test
    void 모든_셀럽들을_조회한다() {
        // given
        List<CelebQueryResponse> expected = List.of(
                new CelebQueryResponse(null, "성시경", "@성시경", "성시경 프로필 url"),
                new CelebQueryResponse(null, "쯔양", "@쯔양", "쯔양 프로필 url"),
                new CelebQueryResponse(null, "마리아주", "@마리아주", "마리아주 프로필 url")
        );

        // when
        List<CelebQueryResponse> celebs = celebQueryResponseDao.find();

        // then
        assertThat(celebs)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}
