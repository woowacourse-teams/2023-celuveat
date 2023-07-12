package com.celuveat.celuveat.admin.domain;

import static com.celuveat.celuveat.admin.exception.AdminExceptionType.NOT_MATCH_PASSWORD;

import com.celuveat.celuveat.admin.exception.AdminException;
import lombok.Builder;

@Builder
public record Admin(
        Long id,
        String name,
        String username,
        String password
) {
    public void login(PasswordMatcher matcher, String rawPassword) {
        boolean isMatch = matcher.isMatch(rawPassword, this.password);
        if (!isMatch) {
            throw new AdminException(NOT_MATCH_PASSWORD);
        }
    }
}
