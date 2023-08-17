package com.celuveat.auth.application.dto;

public record MemberProfileResponse(
        Long memberId,
        String nickname,
        String profileImageUrl
) {
}
