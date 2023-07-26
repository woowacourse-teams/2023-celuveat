package com.celuveat.auth.infra.oauth.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.kakao")
public record KakaoConfig(
        String redirectUri,
        String clientId,
        String clientSecret,
        String[] scope
) {
}

