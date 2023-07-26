package com.celuveat.auth.domain.client;

import static com.celuveat.auth.exception.AuthExceptionType.UNSUPPORTED_OAUTH_TYPE;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthServer;
import com.celuveat.auth.exception.AuthException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class OauthMemberClientComposite {

    private final Map<OauthServer, OauthMemberClient> mapping;

    public OauthMemberClientComposite(Set<OauthMemberClient> clients) {
        mapping = clients.stream()
                .collect(toMap(
                        OauthMemberClient::supportServer,
                        identity()
                ));
    }

    public OauthMember fetch(OauthServer oauthServer, String code) {
        return getClient(oauthServer).fetch(code);
    }

    private OauthMemberClient getClient(OauthServer oauthServer) {
        return Optional.ofNullable(mapping.get(oauthServer))
                .orElseThrow(() -> new AuthException(UNSUPPORTED_OAUTH_TYPE));
    }
}
