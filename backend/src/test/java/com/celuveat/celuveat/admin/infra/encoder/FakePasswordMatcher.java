package com.celuveat.celuveat.admin.infra.encoder;

import com.celuveat.celuveat.admin.domain.PasswordMatcher;

public class FakePasswordMatcher implements PasswordMatcher {

    @Override
    public boolean isMatch(String rawPassword, String encryptedPassword) {
        return rawPassword.equals(encryptedPassword);
    }
}
