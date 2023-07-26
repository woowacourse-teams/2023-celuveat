package com.celuveat.auth.infra.oauth.kakao;

import static com.celuveat.auth.exception.AuthExceptionType.NOT_FOUND_USER;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthServer;
import com.celuveat.auth.domain.client.OauthMemberClient;
import com.celuveat.auth.exception.AuthException;
import com.celuveat.auth.infra.oauth.kakao.dto.KakaoMemberResponse;
import com.celuveat.auth.infra.oauth.kakao.dto.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoMemberClient implements OauthMemberClient {

    private static final String USER_API_URI = "https://kapi.kakao.com/v2/user/me";

    private final KakaoAccessTokenClient kakaoAccessTokenClient;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public OauthServer supportServer() {
        return OauthServer.KAKAO;
    }

    @Override
    public OauthMember fetch(String code) {
        KakaoToken accessToken = kakaoAccessTokenClient.fetch(code);
        KakaoMemberResponse kakaoMemberResponse = getKakaoMember(accessToken.access_token());
        return kakaoMemberResponse.toDomain();
    }

    private KakaoMemberResponse getKakaoMember(String accessToken) {
        HttpHeaders httpHeaders = createUrlEncodedHeader();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<Object> request = new HttpEntity<>(httpHeaders);
        KakaoMemberResponse kakaoMemberResponse = restTemplate
                .exchange(USER_API_URI, GET, request, KakaoMemberResponse.class)
                .getBody();
        if (kakaoMemberResponse == null) {
            throw new AuthException(NOT_FOUND_USER);
        }
        return kakaoMemberResponse;
    }

    private HttpHeaders createUrlEncodedHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE);
        return httpHeaders;
    }
}
