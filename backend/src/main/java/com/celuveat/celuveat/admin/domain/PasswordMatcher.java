package com.celuveat.celuveat.admin.domain;

public interface PasswordMatcher {

    boolean isMatch(String rawPassword, String encryptedPassword);
}
