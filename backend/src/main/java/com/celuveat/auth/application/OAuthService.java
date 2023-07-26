package com.celuveat.auth.application;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.auth.domain.OauthServer;
import com.celuveat.auth.domain.authcode.OauthAuthCodeIssueUrlProviderComposite;
import com.celuveat.auth.domain.client.OauthMemberClientComposite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final OauthAuthCodeIssueUrlProviderComposite oauthAuthCodeIssueUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final OauthMemberRepository oauthMemberRepository;

    public String getAuthorizationCodeIssueUrl(OauthServer oauthServer) {
        return oauthAuthCodeIssueUrlProviderComposite.provide(oauthServer);
    }

    public Long login(OauthServer oauthServer, String code) {
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServer, code);
        OauthMember saved = oauthMemberRepository.findByOauthId(oauthMember.oauthId())
                .orElseGet(() -> oauthMemberRepository.save(oauthMember));
        return saved.id();
    }
}
