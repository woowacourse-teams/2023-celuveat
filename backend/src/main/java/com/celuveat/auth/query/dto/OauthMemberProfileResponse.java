package com.celuveat.auth.query.dto;

public record OauthMemberProfileResponse(
        Long memberId,
        String nickname,
        String profileImageUrl,
        String oauthServer
) {
}
