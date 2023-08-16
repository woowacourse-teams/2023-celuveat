package com.celuveat.auth.application.dto;

public record MemberQueryResponse(
        Long memberId,
        String nickname,
        String profileImageUrl
) {
}
