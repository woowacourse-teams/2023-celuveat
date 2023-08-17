package com.celuveat.auth.domain.client;

import static com.celuveat.auth.exception.AuthExceptionType.UNSUPPORTED_OAUTH_TYPE;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import com.celuveat.auth.domain.OauthId;
import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthServerType;
import com.celuveat.auth.exception.AuthException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class OauthMemberClientComposite {

    private final Map<OauthServerType, OauthMemberClient> clients;

    public OauthMemberClientComposite(Set<OauthMemberClient> clients) {
        this.clients = clients.stream()
                .collect(toMap(OauthMemberClient::supportServer, identity()));
    }

    public OauthMember fetch(OauthServerType oauthServerType, String authCode) {
        return getClient(oauthServerType).fetch(authCode);
    }

    public void logout(OauthServerType oauthServerType, OauthId oauthId) {
        getClient(oauthServerType).logout(oauthId.oauthServerMemberId());
    }

    public void withdraw(OauthServerType oauthServerType, OauthId oauthId) {
        getClient(oauthServerType).withdraw(oauthId.oauthServerMemberId());
    }

    private OauthMemberClient getClient(OauthServerType oauthServerType) {
        return Optional.ofNullable(clients.get(oauthServerType))
                .orElseThrow(() -> new AuthException(UNSUPPORTED_OAUTH_TYPE));
    }
}
