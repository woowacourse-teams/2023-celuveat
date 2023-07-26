package com.celuveat.auth.infra.oauth.google.authcode;

import com.celuveat.auth.domain.OauthServer;
import com.celuveat.auth.domain.authcode.OauthAuthCodeIssueUrlProvider;
import com.celuveat.auth.infra.oauth.google.GoogleConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GoogleAuthCodeIssueUriProvider implements OauthAuthCodeIssueUrlProvider {

    private final GoogleConfig googleConfig;

    @Override
    public OauthServer supportServer() {
        return OauthServer.GOOGLE;
    }

    @Override
    public String provide() {
        return UriComponentsBuilder
                .fromUriString("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("response_type", "code")
                .queryParam("client_id", googleConfig.clientId())
                .queryParam("redirect_uri", googleConfig.redirectUri())
                .queryParam("scope", String.join(",", googleConfig.scope()))
                .build()
                .toUriString();
    }
}
