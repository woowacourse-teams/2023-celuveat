package com.celuveat.celuveat.admin.domain;

public interface PasswordEncoder {

    boolean checkPassword(String rawPassword, String encryptedPassword);
}
