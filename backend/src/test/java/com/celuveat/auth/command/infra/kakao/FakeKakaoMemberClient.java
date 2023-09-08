package com.celuveat.auth.command.infra.kakao;

import static com.celuveat.auth.command.domain.OauthServerType.KAKAO;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthServerType;
import com.celuveat.auth.command.domain.client.OauthMemberClient;

public class FakeKakaoMemberClient implements OauthMemberClient {

    @Override
    public OauthServerType supportServer() {
        return KAKAO;
    }

    @Override
    public OauthMember fetch(String code) {
        return null;
    }

    @Override
    public void logout(String oauthServerMemberId) {
    }

    @Override
    public void withdraw(String oauthServerMemberId) {
    }
}
