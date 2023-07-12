package com.celuveat.celuveat.admin.application;

import static com.celuveat.celuveat.admin.exception.AdminExceptionType.NOT_FOUND_ADMIN;
import static com.celuveat.celuveat.admin.exception.AdminExceptionType.NOT_MATCH_PASSWORD;
import static com.celuveat.celuveat.admin.fixture.AdminFixture.관리자_도기;
import static com.celuveat.celuveat.admin.fixture.AdminFixture.관리자_오도;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.celuveat.admin.domain.Admin;
import com.celuveat.celuveat.admin.domain.PasswordMatcher;
import com.celuveat.celuveat.admin.exception.AdminException;
import com.celuveat.celuveat.admin.infra.encoder.FakePasswordMatcher;
import com.celuveat.celuveat.admin.infra.persistence.FakeAdminDao;
import com.celuveat.celuveat.common.exception.BaseExceptionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("AdminService 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class AdminServiceTest {

    private final FakeAdminDao fakeAdminDao = new FakeAdminDao();
    private final PasswordMatcher matcher = new FakePasswordMatcher();
    private final AdminService adminService = new AdminService(fakeAdminDao, matcher);

    @BeforeEach
    void setUp() {
        fakeAdminDao.save(관리자_오도());
        fakeAdminDao.save(관리자_도기());
    }

    @Test
    void 입력된_아이디가_존재하지_않으면_예외를_발생한다() {
        // when
        BaseExceptionType exceptionType = assertThrows(AdminException.class, () ->
                adminService.login("Empty User Name", "Empty Password")
        ).exceptionType();

        // then
        assertThat(exceptionType).isEqualTo(NOT_FOUND_ADMIN);
    }

    @Test
    void 입력된_비밀번호가_일치하지_않으면_예외를_발생한다() {
        // when
        BaseExceptionType exceptionType = assertThrows(AdminException.class, () ->
                adminService.login(관리자_도기().username(), "Wrong Password")
        ).exceptionType();

        // then
        assertThat(exceptionType).isEqualTo(NOT_MATCH_PASSWORD);
    }

    @Test
    void 로그인_성공() {
        // given
        Admin 도기 = 관리자_도기();

        // then
        assertThatNoException().isThrownBy(
                () -> adminService.login(도기.username(), 도기.password())
        );
    }
}
