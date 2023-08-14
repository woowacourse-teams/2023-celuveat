package com.celuveat.auth.application.dto;

public record MemberQueryResponse(
        String nickname,
        String profileImageUrl
) {
}
