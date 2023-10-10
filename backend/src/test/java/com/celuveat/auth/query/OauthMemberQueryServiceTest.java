package com.celuveat.auth.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.celuveat.auth.query.dao.OauthMemberProfileResponseDao;
import com.celuveat.auth.query.dto.OauthMemberProfileResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("멤버 조회용 서비스(MemberQueryService) 은(는)")
class OauthMemberQueryServiceTest {

    private final OauthMemberProfileResponseDao oauthMemberProfileResponseDao =
            mock(OauthMemberProfileResponseDao.class);
    private final OauthMemberQueryService oauthMemberQueryService =
            new OauthMemberQueryService(oauthMemberProfileResponseDao);

    @Test
    void 회원정보를_조회한다() {
        // given
        OauthMemberProfileResponse expected = new OauthMemberProfileResponse(
                1L, "오도", "https://odo.jpg", "KAKAO"
        );
        given(oauthMemberProfileResponseDao.find(1L)).willReturn(expected);

        // when
        OauthMemberProfileResponse result = oauthMemberQueryService.getProfile(1L);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
