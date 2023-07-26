package com.celuveat.auth.application;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.auth.domain.OauthServer;
import com.celuveat.auth.domain.authcode.OauthAuthCodeIssueUrlProvider;
import com.celuveat.auth.domain.authcode.OauthAuthCodeIssueUrlProviderMapping;
import com.celuveat.auth.domain.client.OauthMemberClient;
import com.celuveat.auth.domain.client.OauthMemberClientMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final OauthAuthCodeIssueUrlProviderMapping oauthAuthCodeIssueUrlProviderMapping;
    private final OauthMemberClientMapping oauthMemberClientMapping;
    private final OauthMemberRepository oauthMemberRepository;

    public String getAuthorizationCodeIssueUrl(OauthServer oauthServer) {
        OauthAuthCodeIssueUrlProvider provider =
                oauthAuthCodeIssueUrlProviderMapping.getProvider(oauthServer);
        return provider.provide();
    }

    public Long login(OauthServer oauthServer, String code) {
        OauthMemberClient oauthMemberClient = oauthMemberClientMapping.getClient(oauthServer);
        OauthMember oauthMember = oauthMemberClient.fetch(code);
        oauthMemberRepository.findByOauthId(oauthMember.oauthId())
                .orElseGet(() -> oauthMemberRepository.save(oauthMember));
        return oauthMember.id();
    }
}
