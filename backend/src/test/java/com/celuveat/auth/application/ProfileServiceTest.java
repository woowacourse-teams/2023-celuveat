package com.celuveat.auth.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.application.dto.ProfileResponse;
import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.common.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@DisplayName("프로필서비스(ProfileService) 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private OauthMemberRepository oauthMemberRepository;

    @Test
    void 회원정보를_조회한다() {
        // given
        OauthMember 오도 = 멤버("오도");
        oauthMemberRepository.save(오도);
        ProfileResponse expected = new ProfileResponse("오도", "abc");

        // when
        ProfileResponse result = profileService.getProfile(오도.id());

        // then
        assertThat(result).isEqualTo(expected);
    }
}
