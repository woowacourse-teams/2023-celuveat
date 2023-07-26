package com.celuveat.auth.infra.oauth.naver;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthServer;
import com.celuveat.auth.domain.client.OauthMemberClient;
import com.celuveat.auth.infra.oauth.naver.client.NaverApiClient;
import com.celuveat.auth.infra.oauth.naver.dto.NaverMemberResponse;
import com.celuveat.auth.infra.oauth.naver.dto.NaverToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class NaverMemberClient implements OauthMemberClient {

    private final NaverApiClient naverApiClient;
    private final NaverConfig naverConfig;

    @Override
    public OauthServer supportServer() {
        return OauthServer.NAVER;
    }

    @Override
    public OauthMember fetch(String code) {
        NaverToken accessToken = naverApiClient.fetchAccessToken(tokenRequestBody(code));
        NaverMemberResponse naverMemberResponse = naverApiClient.fetchMember("Bearer " + accessToken.accessToken());
        return naverMemberResponse.toDomain();
    }
    
    private MultiValueMap<String, String> tokenRequestBody(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", naverConfig.clientId());
        body.add("client_secret", naverConfig.clientSecret());
        body.add("code", code);
        body.add("state", "test");
        return body;
    }
}
