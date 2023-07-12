package com.celuveat.celuveat.admin.infra.encoder;

import com.celuveat.celuveat.admin.domain.PasswordMatcher;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordMatcher implements PasswordMatcher {

    @Override
    public boolean isMatch(String rawPassword, String encryptedPassword) {
        return BCrypt.checkpw(rawPassword, encryptedPassword);
    }
}
