package com.celuveat.celuveat.admin.infra.persistence;

import static com.celuveat.celuveat.admin.exception.AdminExceptionType.NOT_FOUND_ADMIN;
import static com.celuveat.celuveat.admin.fixture.AdminFixture.관리자_도기;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.celuveat.admin.domain.Admin;
import com.celuveat.celuveat.admin.exception.AdminException;
import com.celuveat.celuveat.common.annotation.DaoTest;
import com.celuveat.celuveat.common.exception.BaseExceptionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DaoTest
@DisplayName("AdminDao 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class AdminDaoTest {

    @Autowired
    private AdminDao adminDao;

    @Test
    void 존재하지_않는_이름으로_조회시_예외가_발생한다() {
        // when
        BaseExceptionType exceptionType = assertThrows(AdminException.class, () ->
                adminDao.getByUsername("존재하지 않는 이름"))
                .exceptionType();

        // then
        assertThat(exceptionType).isEqualTo(NOT_FOUND_ADMIN);
    }

    @Test
    void 이름으로_관리자를_찾는다() {
        // given
        Admin 도기 = 관리자_도기();
        adminDao.save(도기);

        // when
        Admin result = adminDao.getByUsername(도기.username());

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(도기);
    }
}
