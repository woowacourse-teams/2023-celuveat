package com.celuveat.celeb.query.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.celeb.query.dto.FindAllCelebResponse;
import com.celuveat.common.DaoTest;
import com.celuveat.common.TestData;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("셀럽 전체조회 DAO(FindAllCelebResponseDao) 은(는)")
class FindAllCelebResponseDaoTest extends DaoTest {

    @Autowired
    private FindAllCelebResponseDao findAllCelebResponseDao;

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
        List<FindAllCelebResponse> expected = List.of(
                new FindAllCelebResponse(null, "성시경", "@성시경", "성시경 프로필 url"),
                new FindAllCelebResponse(null, "쯔양", "@쯔양", "쯔양 프로필 url"),
                new FindAllCelebResponse(null, "마리아주", "@마리아주", "마리아주 프로필 url")
        );

        // when
        List<FindAllCelebResponse> celebs = findAllCelebResponseDao.findAll();

        // then
        assertThat(celebs)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}
