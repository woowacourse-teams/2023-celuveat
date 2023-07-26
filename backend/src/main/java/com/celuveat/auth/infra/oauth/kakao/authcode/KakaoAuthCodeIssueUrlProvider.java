package com.celuveat.auth.infra.oauth.kakao.authcode;

import com.celuveat.auth.domain.OauthServer;
import com.celuveat.auth.domain.authcode.OauthAuthCodeIssueUrlProvider;
import com.celuveat.auth.infra.oauth.kakao.KakaoConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoAuthCodeIssueUrlProvider implements OauthAuthCodeIssueUrlProvider {

    private final KakaoConfig kakaoConfig;

    @Override
    public OauthServer supportServer() {
        return OauthServer.KAKAO;
    }

    @Override
    public String provide() {
        return UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoConfig.clientId())
                .queryParam("redirect_url", kakaoConfig.redirectUri())
                .queryParam("scope", String.join(",", kakaoConfig.scope()))
                .toUriString();
    }
}
