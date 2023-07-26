package com.celuveat.auth.infra.oauth.google.dto;

import static com.celuveat.auth.domain.OauthServer.GOOGLE;

import com.celuveat.auth.domain.OauthId;
import com.celuveat.auth.domain.OauthMember;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GoogleMemberResponse(
        String id,
        String name,
        String givenName,
        String picture,
        String locale
) {

    public OauthMember toDomain() {
        return OauthMember.builder()
                .oauthId(new OauthId(id, GOOGLE))
                .nickname(givenName)
                .profileImagePath(picture)
                .build();
    }
}
