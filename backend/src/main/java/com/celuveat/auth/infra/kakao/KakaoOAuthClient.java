package com.celuveat.auth.infra.kakao;

import static com.celuveat.auth.exception.AuthExceptionType.NOT_FOUND_ACCESS_TOKEN;
import static com.celuveat.auth.exception.AuthExceptionType.NOT_FOUND_USER;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

import com.celuveat.auth.domain.OAuthClient;
import com.celuveat.auth.exception.AuthException;
import com.celuveat.auth.infra.kakao.dto.TokenResponse;
import com.celuveat.auth.infra.kakao.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements OAuthClient {

    private static final String REDIRECT_URI = "http://localhost:8080/api/oauth/kakao";
    private static final String TOKEN_API_URI = "https://kauth.kakao.com/oauth/token";
    private static final String USER_API_URI = "https://kapi.kakao.com/v2/user/me";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.client_secret}")
    private String clientSecret;

    @Override
    public Long login(String code) {
        String accessToken = getAccessToken(code);
        return getOAuthId(accessToken);
    }

    private String getAccessToken(String code) {
        MultiValueMap<String, String> requestBody = createPostTokenRequestBody(code);
        HttpEntity<MultiValueMap<String, String>> postRequest = new HttpEntity<>(requestBody, createUrlEncodedHeader());
        TokenResponse tokenResponse = restTemplate.postForObject(TOKEN_API_URI, postRequest, TokenResponse.class);
        if (tokenResponse == null) {
            throw new AuthException(NOT_FOUND_ACCESS_TOKEN);
        }
        return tokenResponse.access_token();
    }

    private MultiValueMap<String, String> createPostTokenRequestBody(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("code", code);
        body.add("client_secret", clientSecret);
        return body;
    }

    private HttpHeaders createUrlEncodedHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE);
        return httpHeaders;
    }

    private Long getOAuthId(String accessToken) {
        HttpHeaders httpHeaders = createUrlEncodedHeader();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<Object> request = new HttpEntity<>(httpHeaders);
        UserResponse userResponse = restTemplate.exchange(USER_API_URI, HttpMethod.GET, request, UserResponse.class)
                .getBody();
        if (userResponse == null) {
            throw new AuthException(NOT_FOUND_USER);
        }
        return userResponse.id();
    }
}
