package com.celuveat.auth.infra.oauth.naver.client;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.celuveat.auth.infra.oauth.naver.dto.NaverMemberResponse;
import com.celuveat.auth.infra.oauth.naver.dto.NaverToken;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface NaverApiClient {

    @PostExchange(url = "https://nid.naver.com/oauth2.0/token")
    NaverToken fetchAccessToken(@RequestParam MultiValueMap<String, String> body);

    @GetExchange("https://openapi.naver.com/v1/nid/me")
    NaverMemberResponse fetchMember(@RequestHeader(name = AUTHORIZATION) String bearerToken);
}
