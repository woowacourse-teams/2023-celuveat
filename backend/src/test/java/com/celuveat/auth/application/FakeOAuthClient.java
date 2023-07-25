package com.celuveat.auth.application;

import com.celuveat.auth.domain.OAuthClient;

public class FakeOAuthClient implements OAuthClient {

    @Override
    public Long login(String code) {
        return 1L;
    }
}
