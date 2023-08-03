package com.celuveat.auth.domain.authcode;

import com.celuveat.auth.domain.OauthServerType;

public interface AuthCodeRequestUrlProvider {

    OauthServerType supportServer();

    String provide();
}
