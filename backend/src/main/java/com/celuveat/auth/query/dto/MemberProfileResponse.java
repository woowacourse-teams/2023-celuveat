package com.celuveat.auth.query.dto;

public record MemberProfileResponse(
        Long memberId,
        String nickname,
        String profileImageUrl
) {
}
