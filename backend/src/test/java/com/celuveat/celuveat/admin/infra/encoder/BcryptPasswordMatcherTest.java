package com.celuveat.celuveat.admin.infra.encoder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

@DisplayName("BcryptPasswordEncoder 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class BcryptPasswordMatcherTest {

    private final BcryptPasswordMatcher matcher = new BcryptPasswordMatcher();

    @Test
    void 암호화된_비밀번호와_입력된_비밀번호가_같은지_확인한다() {
        // given
        String encryptedPassword = BCrypt.hashpw("1234", BCrypt.gensalt());
        String rawPassword = "1234";

        // when
        boolean correctResult = matcher.isMatch(rawPassword, encryptedPassword);
        boolean wrongResult = matcher.isMatch(rawPassword + "4321", encryptedPassword);

        // then
        assertThat(correctResult).isTrue();
        assertThat(wrongResult).isFalse();
    }
}
