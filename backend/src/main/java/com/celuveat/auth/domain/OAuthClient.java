package com.celuveat.auth.domain;

public interface OAuthClient {

    Long login(String code);
}
