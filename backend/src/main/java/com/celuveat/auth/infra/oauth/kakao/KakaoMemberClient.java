package com.celuveat.auth.infra.oauth.kakao;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthServer;
import com.celuveat.auth.domain.client.OauthMemberClient;
import com.celuveat.auth.infra.oauth.kakao.client.KakaoApiClient;
import com.celuveat.auth.infra.oauth.kakao.dto.KakaoMemberResponse;
import com.celuveat.auth.infra.oauth.kakao.dto.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class KakaoMemberClient implements OauthMemberClient {

    private final KakaoApiClient kakaoApiClient;
    private final KakaoConfig kakaoConfig;

    @Override
    public OauthServer supportServer() {
        return OauthServer.KAKAO;
    }

    @Override
    public OauthMember fetch(String code) {
        KakaoToken accessToken = kakaoApiClient.fetchAccessToken(tokenRequestBody(code));
        KakaoMemberResponse kakaoMemberResponse = getKakaoMember(accessToken.access_token());
        return kakaoMemberResponse.toDomain();
    }

    private MultiValueMap<String, String> tokenRequestBody(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoConfig.clientId());
        body.add("redirect_uri", kakaoConfig.redirectUri());
        body.add("code", code);
        body.add("client_secret", kakaoConfig.clientSecret());
        return body;
    }

    private KakaoMemberResponse getKakaoMember(String accessToken) {
        return kakaoApiClient.fetchMember("Bearer " + accessToken);
    }
}
