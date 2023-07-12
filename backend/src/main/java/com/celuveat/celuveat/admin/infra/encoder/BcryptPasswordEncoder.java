package com.celuveat.celuveat.admin.infra.encoder;

import com.celuveat.celuveat.admin.domain.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncoder implements PasswordEncoder {

    public boolean checkPassword(String rawPassword, String encryptedPassword) {
        return BCrypt.checkpw(rawPassword, encryptedPassword);
    }
}
