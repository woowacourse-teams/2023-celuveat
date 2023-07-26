package com.celuveat.auth.infra.oauth.kakao;

import static com.celuveat.auth.exception.AuthExceptionType.NOT_FOUND_ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

import com.celuveat.auth.exception.AuthException;
import com.celuveat.auth.infra.oauth.kakao.dto.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoAccessTokenClient {

    private static final String TOKEN_API_URI = "https://kauth.kakao.com/oauth/token";

    private final RestTemplate restTemplate = new RestTemplate(); // TODO 변경
    private final KakaoConfig kakaoConfig;

    public KakaoToken fetch(String code) {
        MultiValueMap<String, String> requestBody = createPostTokenRequestBody(code);
        HttpEntity<MultiValueMap<String, String>> postRequest = new HttpEntity<>(requestBody, createUrlEncodedHeader());
        KakaoToken kakaoTokenResponse =
                restTemplate.postForObject(TOKEN_API_URI, postRequest, KakaoToken.class);
        if (kakaoTokenResponse == null) {
            throw new AuthException(NOT_FOUND_ACCESS_TOKEN);
        }
        return kakaoTokenResponse;
    }

    private HttpHeaders createUrlEncodedHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE);
        return httpHeaders;
    }

    private MultiValueMap<String, String> createPostTokenRequestBody(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoConfig.clientId());
        body.add("redirect_uri", kakaoConfig.redirectUri());
        body.add("code", code);
        body.add("client_secret", kakaoConfig.clientSecret());
        return body;
    }
}
