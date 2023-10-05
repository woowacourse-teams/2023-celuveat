package com.celuveat.auth.query;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.query.dto.OauthMemberProfileResponse;
import com.celuveat.common.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("멤버 조회용 서비스(MemberQueryService) 은(는)")
class OauthMemberQueryServiceTest extends IntegrationTest {

    @Test
    void 회원정보를_조회한다() {
        // given
        OauthMember 오도 = oauthMemberRepository.save(멤버("오도"));
        OauthMemberProfileResponse expected = new OauthMemberProfileResponse(오도.id(), "오도", "abc", "KAKAO");

        // when
        OauthMemberProfileResponse result = oauthMemberQueryService.getProfile(오도.id());

        // then
        assertThat(result).isEqualTo(expected);
    }
}
