package com.celuveat.auth.domain;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class OauthId {

    @Column(nullable = false, name = "oauth_server_member_id")
    private String oauthServerMemberId;

    @Enumerated(STRING)
    @Column(nullable = false, name = "oauth_server")
    private OauthServerType oauthServerType;

    public String oauthServerMemberId() {
        return oauthServerMemberId;
    }

    public OauthServerType oauthServer() {
        return oauthServerType;
    }
}
