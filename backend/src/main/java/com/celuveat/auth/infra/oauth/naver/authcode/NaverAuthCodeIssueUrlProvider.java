package com.celuveat.auth.infra.oauth.naver.authcode;

import com.celuveat.auth.domain.OauthServer;
import com.celuveat.auth.domain.authcode.OauthAuthCodeIssueUrlProvider;
import com.celuveat.auth.infra.oauth.naver.NaverConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class NaverAuthCodeIssueUrlProvider implements OauthAuthCodeIssueUrlProvider {

    private final NaverConfig naverConfig;

    @Override
    public OauthServer supportServer() {
        return OauthServer.NAVER;
    }

    @Override
    public String provide() {
        return UriComponentsBuilder
                .fromUriString("https://nid.naver.com/oauth2.0/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", naverConfig.clientId())
                .queryParam("redirect_url", naverConfig.redirectUri())
                .queryParam("state", "test")
                .build()
                .toUriString();
    }
}
