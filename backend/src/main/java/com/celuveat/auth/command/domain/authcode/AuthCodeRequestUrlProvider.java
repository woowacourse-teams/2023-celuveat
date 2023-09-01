package com.celuveat.auth.command.domain.authcode;

import com.celuveat.auth.command.domain.OauthServerType;

public interface AuthCodeRequestUrlProvider {

    OauthServerType supportServer();

    String provide();
}
