package com.celuveat.auth.infra.oauth.kakao;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@AllArgsConstructor
@ConfigurationProperties(prefix = "oauth.kakao")
public class KakaoConfig {

    private final String redirectUri;
    private final String clientId;
    private final String clientSecret;
    private final String[] scope;

    public String redirectUri() {
        return redirectUri;
    }

    public String clientId() {
        return clientId;
    }

    public String clientSecret() {
        return clientSecret;
    }

    public String[] scope() {
        return scope;
    }
}
