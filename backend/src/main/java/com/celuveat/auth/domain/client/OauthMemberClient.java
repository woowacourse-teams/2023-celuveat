package com.celuveat.auth.domain.client;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthServerType;

public interface OauthMemberClient {

    OauthServerType supportServer();

    OauthMember fetch(String code);

    Long logout(String oauthServerId);
}
