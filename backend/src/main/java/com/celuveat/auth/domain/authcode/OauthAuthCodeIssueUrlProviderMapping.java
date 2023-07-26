package com.celuveat.auth.domain.authcode;

import static com.celuveat.auth.exception.AuthExceptionType.UNSUPPORTED_OAUTH_TYPE;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import com.celuveat.auth.domain.OauthServer;
import com.celuveat.auth.exception.AuthException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class OauthAuthCodeIssueUrlProviderMapping {

    private final Map<OauthServer, OauthAuthCodeIssueUrlProvider> mapping;

    public OauthAuthCodeIssueUrlProviderMapping(Set<OauthAuthCodeIssueUrlProvider> providers) {
        mapping = providers.stream()
                .collect(toMap(
                        OauthAuthCodeIssueUrlProvider::supportServer,
                        identity()
                ));
    }

    public OauthAuthCodeIssueUrlProvider getProvider(OauthServer oauthServer) {
        return Optional.ofNullable(mapping.get(oauthServer))
                .orElseThrow(() -> new AuthException(UNSUPPORTED_OAUTH_TYPE));
    }
}
