package com.celuveat.auth.fixture;

import static com.celuveat.auth.domain.OauthServerType.KAKAO;

import com.celuveat.auth.domain.OauthId;
import com.celuveat.auth.domain.OauthMember;

public class OauthMemberFixture {

    public static OauthMember 멤버(String name) {
        return OauthMember.builder()
                .oauthId(new OauthId("123", KAKAO))
                .nickname(name)
                .profileImageUrl("abc")
                .build();
    }
}
