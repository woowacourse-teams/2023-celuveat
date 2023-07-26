package com.celuveat.auth.domain.client;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthServer;

public interface OauthMemberClient {

    OauthServer supportServer();

    OauthMember fetch(String code);
}
