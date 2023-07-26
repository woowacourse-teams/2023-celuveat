package com.celuveat.auth.domain.authcode;

import com.celuveat.auth.domain.OauthServer;

public interface OauthAuthCodeIssueUrlProvider {

    OauthServer supportServer();

    String provide();
}
