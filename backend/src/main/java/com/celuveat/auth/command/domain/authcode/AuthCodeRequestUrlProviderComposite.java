package com.celuveat.auth.command.domain.authcode;

import static com.celuveat.auth.exception.AuthExceptionType.UNSUPPORTED_OAUTH_TYPE;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import com.celuveat.auth.command.domain.OauthServerType;
import com.celuveat.auth.exception.AuthException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class AuthCodeRequestUrlProviderComposite {

    private final Map<OauthServerType, AuthCodeRequestUrlProvider> providers;

    public AuthCodeRequestUrlProviderComposite(Set<AuthCodeRequestUrlProvider> providers) {
        this.providers = providers.stream()
                .collect(toMap(
                        AuthCodeRequestUrlProvider::supportServer,
                        identity()
                ));
    }

    public String provide(OauthServerType oauthServerType) {
        return getProvider(oauthServerType).provide();
    }

    private AuthCodeRequestUrlProvider getProvider(OauthServerType oauthServerType) {
        return Optional.ofNullable(providers.get(oauthServerType))
                .orElseThrow(() -> new AuthException(UNSUPPORTED_OAUTH_TYPE));
    }
}
