package com.celuveat.auth.fixture;

import static com.celuveat.auth.command.domain.OauthServerType.KAKAO;

import com.celuveat.auth.command.domain.OauthId;
import com.celuveat.auth.command.domain.OauthMember;

public class OauthMemberFixture {

    public static OauthMember 말랑() {
        return OauthMember.builder()
                .oauthId(new OauthId("mallang", KAKAO))
                .nickname("말랑")
                .profileImageUrl("https://mallang.jpg")
                .build();
    }

    public static OauthMember 도기() {
        return OauthMember.builder()
                .oauthId(new OauthId("doggy", KAKAO))
                .nickname("도기")
                .profileImageUrl("https://doggy.jpg")
                .build();
    }

    public static OauthMember 오도() {
        return OauthMember.builder()
                .oauthId(new OauthId("odo", KAKAO))
                .nickname("오도")
                .profileImageUrl("https://odo.jpg")
                .build();
    }

    public static OauthMember 로이스() {
        return OauthMember.builder()
                .oauthId(new OauthId("royce", KAKAO))
                .nickname("로이스")
                .profileImageUrl("https://royce.jpg")
                .build();
    }

    public static OauthMember 멤버(String name) {
        return OauthMember.builder()
                .oauthId(new OauthId(name, KAKAO))
                .nickname(name)
                .profileImageUrl("https://%s.jpg".formatted(name))
                .build();
    }
}
