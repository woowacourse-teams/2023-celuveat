package com.celuveat.celuveat.admin.infra.encoder;

import com.celuveat.celuveat.admin.domain.PasswordEncoder;

public class FakePasswordEncoder implements PasswordEncoder {

    @Override
    public boolean checkPassword(String rawPassword, String encryptedPassword) {
        return rawPassword.equals(encryptedPassword);
    }
}
