package com.celuveat.auth.infra.oauth.google.client;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthServerType;
import com.celuveat.auth.domain.client.OauthMemberClient;
import com.celuveat.auth.infra.oauth.google.GoogleOauthConfig;
import com.celuveat.auth.infra.oauth.google.dto.GoogleMemberResponse;
import com.celuveat.auth.infra.oauth.google.dto.GoogleToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class GoogleMemberClient implements OauthMemberClient {

    private final GoogleApiClient googleApiClient;
    private final GoogleOauthConfig googleOauthConfig;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.GOOGLE;
    }

    @Override
    public OauthMember fetch(String authCode) {
        GoogleToken tokenInfo = googleApiClient.fetchToken(tokenRequestParams(authCode));
        GoogleMemberResponse googleMemberResponse =
                googleApiClient.fetchMember("Bearer " + tokenInfo.accessToken());
        return googleMemberResponse.toDomain();
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", googleOauthConfig.clientId());
        params.add("client_secret", googleOauthConfig.clientSecret());
        params.add("code", authCode);
        params.add("redirect_uri", googleOauthConfig.redirectUri());
        return params;
    }

    @Override
    public void logout(String oauthServerId) {
    }

    @Override
    public void withDraw(String oauthServerId) {
    }
}
