package com.celuveat.auth.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.application.dto.MemberQueryResponse;
import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.common.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@DisplayName("멤버 조회용 서비스(MemberQueryService) 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberQueryServiceTest {

    @Autowired
    private MemberQueryService memberQueryService;

    @Autowired
    private OauthMemberRepository oauthMemberRepository;

    @Test
    void 회원정보를_조회한다() {
        // given
        OauthMember 오도 = 멤버("오도");
        oauthMemberRepository.save(오도);
        MemberQueryResponse expected = new MemberQueryResponse(오도.id(), "오도", "abc");

        // when
        MemberQueryResponse result = memberQueryService.getProfile(오도.id());

        // then
        assertThat(result).isEqualTo(expected);
    }
}
