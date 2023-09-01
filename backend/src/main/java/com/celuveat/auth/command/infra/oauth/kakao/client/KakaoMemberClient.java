package com.celuveat.auth.command.infra.oauth.kakao.client;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthServerType;
import com.celuveat.auth.command.domain.client.OauthMemberClient;
import com.celuveat.auth.command.infra.oauth.kakao.KakaoOauthConfig;
import com.celuveat.auth.command.infra.oauth.kakao.dto.KakaoMemberResponse;
import com.celuveat.auth.command.infra.oauth.kakao.dto.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class KakaoMemberClient implements OauthMemberClient {

    private final KakaoApiClient kakaoApiClient;
    private final KakaoOauthConfig kakaoOauthConfig;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.KAKAO;
    }

    @Override
    public OauthMember fetch(String authCode) {
        KakaoToken tokenInfo = kakaoApiClient.fetchToken(tokenRequestParams(authCode));
        KakaoMemberResponse kakaoMemberResponse =
                kakaoApiClient.fetchMember("Bearer " + tokenInfo.accessToken());
        return kakaoMemberResponse.toDomain();
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoOauthConfig.clientId());
        params.add("redirect_uri", kakaoOauthConfig.redirectUri());
        params.add("code", authCode);
        params.add("client_secret", kakaoOauthConfig.clientSecret());
        return params;
    }

    @Override
    public void logout(String oauthServerMemberId) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", oauthServerMemberId);
        kakaoApiClient.logoutMember("KakaoAK " + kakaoOauthConfig.adminKey(), params);
    }

    @Override
    public void withdraw(String oauthServerMemberId) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", oauthServerMemberId);
        kakaoApiClient.withdrawMember("KakaoAK " + kakaoOauthConfig.adminKey(), params);
    }
}
