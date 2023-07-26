package com.celuveat.auth.infra.oauth.kakao.authcode;

import com.celuveat.auth.domain.OauthServer;
import com.celuveat.auth.domain.authcode.OauthAuthCodeIssueUrlProvider;
import com.celuveat.auth.infra.oauth.kakao.KakaoConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoAuthCodeIssueUrlProvider implements OauthAuthCodeIssueUrlProvider {

    private final KakaoConfig kakaoConfig;

    @Override
    public OauthServer supportServer() {
        return OauthServer.KAKAO;
    }

    @Override
    public String provide() {
        return URL.builder()
                .clientId(kakaoConfig.clientId())
                .redirectUri(kakaoConfig.redirectUri())
                .scope(kakaoConfig.scope())
                .build();
    }

    private static class URL {
        private static final String BASE_URL = """
                https://kauth.kakao.com/oauth/authorize
                ?response_type=code
                &client_id=%s
                &redirect_uri=%s
                &scope=%s
                """.replaceAll("\\s+", "");
        private String clientId;
        private String redirectUri;
        private String[] scope;

        private static URL builder() {
            return new URL();
        }

        private URL clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        private URL redirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        private URL scope(String... scope) {
            this.scope = scope;
            return this;
        }

        private String build() {
            return BASE_URL.formatted(clientId, redirectUri, String.join(",", scope));
        }
    }
}
