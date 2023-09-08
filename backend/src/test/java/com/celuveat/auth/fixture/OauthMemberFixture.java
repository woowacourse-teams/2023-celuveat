package com.celuveat.auth.fixture;

import static com.celuveat.auth.command.domain.OauthServerType.KAKAO;

import com.celuveat.auth.command.domain.OauthId;
import com.celuveat.auth.command.domain.OauthMember;

public class OauthMemberFixture {

    public static OauthMember 멤버(String name) {
        return OauthMember.builder()
                .oauthId(new OauthId(name, KAKAO))
                .nickname(name)
                .profileImageUrl("abc")
                .build();
    }
}
