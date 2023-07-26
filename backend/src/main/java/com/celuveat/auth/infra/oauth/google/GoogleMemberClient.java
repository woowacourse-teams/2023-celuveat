package com.celuveat.auth.infra.oauth.google;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthServer;
import com.celuveat.auth.domain.client.OauthMemberClient;
import com.celuveat.auth.infra.oauth.google.client.GoogleApiClient;
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
    private final GoogleConfig googleConfig;

    @Override
    public OauthServer supportServer() {
        return OauthServer.GOOGLE;
    }

    @Override
    public OauthMember fetch(String code) {
        GoogleToken accessToken = googleApiClient.fetchAccessToken(tokenRequestBody(code));
        GoogleMemberResponse googleMemberResponse = googleApiClient.fetchMember("Bearer " + accessToken.accessToken());
        return googleMemberResponse.toDomain();
    }

    private MultiValueMap<String, String> tokenRequestBody(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", googleConfig.clientId());
        body.add("client_secret", googleConfig.clientSecret());
        body.add("code", code);
        body.add("redirect_uri", googleConfig.redirectUri());
        return body;
    }
}
