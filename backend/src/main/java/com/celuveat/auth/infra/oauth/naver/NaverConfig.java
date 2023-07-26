package com.celuveat.auth.infra.oauth.naver;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.naver")
public record NaverConfig(
        String redirectUri,
        String clientId,
        String clientSecret,
        String[] scope
) {
}
