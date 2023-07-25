package com.celuveat.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("AuthService 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void 로그인시_존재하는_않는_회원이면_회원가입시킨다() {
        Member member = authService.login("code", new FakeOAuthClient());
        assertThat(member.id()).isNotNull();
        assertThat(member.oAuthId()).isEqualTo(1L);
    }

    @Test
    void 로그인시_존재하는_회원이면_존재하는_정보를_반환한다() {
        Member member = authService.login("code", new FakeOAuthClient());
        Member member2 = authService.login("code", new FakeOAuthClient());
        assertThat(member).usingRecursiveComparison().ignoringFields("createdDate").isEqualTo(member2);
    }
}
