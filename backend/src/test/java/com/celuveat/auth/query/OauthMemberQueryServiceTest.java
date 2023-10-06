package com.celuveat.auth.query;

import static com.celuveat.auth.command.domain.OauthServerType.KAKAO;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthId;
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
        OauthMember odo = OauthMember.builder()
                .oauthId(new OauthId("odo", KAKAO))
                .nickname("오도")
                .profileImageUrl("https://odo.jpg")
                .build();
        oauthMemberRepository.save(odo);
        OauthMemberProfileResponse expected = new OauthMemberProfileResponse(
                odo.id(), "오도", "https://odo.jpg", "KAKAO"
        );

        // when
        OauthMemberProfileResponse result = oauthMemberQueryService.getProfile(odo.id());

        // then
        assertThat(result).isEqualTo(expected);
    }
}
