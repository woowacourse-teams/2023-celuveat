package com.celuveat.auth.infra.oauth.google;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.google")
public record GoogleConfig(
        String redirectUri,
        String clientId,
        String clientSecret,
        String[] scope
) {
}
