package com.celuveat.auth.command.domain.client;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthServerType;

public interface OauthMemberClient {

    OauthServerType supportServer();

    OauthMember fetch(String code);

    void logout(String oauthServerMemberId);

    void withdraw(String oauthServerMemberId);
}
