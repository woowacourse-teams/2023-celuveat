package com.celuveat.celuveat.admin.domain;

import static com.celuveat.celuveat.admin.exception.AdminExceptionType.NOT_MATCH_PASSWORD;
import static com.celuveat.celuveat.admin.fixture.AdminFixture.관리자_도기;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.celuveat.admin.exception.AdminException;
import com.celuveat.celuveat.admin.infra.encoder.BcryptPasswordEncoder;
import com.celuveat.celuveat.common.exception.BaseExceptionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

@DisplayName("Admin 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class AdminTest {

    private final BcryptPasswordEncoder bcryptPasswordEncoder = new BcryptPasswordEncoder();

    @Test
    void 입력받은_비밀번호가_일치하지_않으면_예외를_발생한다() {
        // given
        Admin 도기 = 관리자_도기();
        String expected = BCrypt.hashpw(도기.password(), BCrypt.gensalt());

        // when
        BaseExceptionType exceptionType = assertThrows(AdminException.class, () ->
                도기.login(bcryptPasswordEncoder, expected + "extra Password")
        ).exceptionType();

        // then
        assertThat(exceptionType).isEqualTo(NOT_MATCH_PASSWORD);
    }
}
