package com.ann.spending.user.service.validator;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncodeManager {

    private final PasswordEncoder passwordEncoder;

    public PasswordEncodeManager(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isPasswordsMatch(String passwordToCheck, String currentPassword) {

        return passwordEncoder.matches(passwordToCheck, currentPassword);
    }

    public String encodeString(String password) {
        return passwordEncoder.encode(password);
    }

}
