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
    public void login(PasswordEncoder encoder, String rawPassword) {
        boolean isMatch = encoder.checkPassword(this.password, rawPassword);
        if (!isMatch) {
            throw new AdminException(NOT_MATCH_PASSWORD);
        }
    }
}
