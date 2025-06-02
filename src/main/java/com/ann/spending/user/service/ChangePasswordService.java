package com.ann.spending.user.service;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.service.repository.UserRepositoryService;
import com.ann.spending.exception.user.PasswordDoesNotMatchException;
import com.ann.spending.user.dto.ChangePasswordRequest;
import com.ann.spending.user.service.validator.PasswordEncodeManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangePasswordService {

    private final UserRepositoryService userRepositoryService;
    private final PasswordEncodeManager passwordMatchValidator;

    public ChangePasswordService(UserRepositoryService userRepositoryService, PasswordEncodeManager passwordMatchValidator) {
        this.userRepositoryService = userRepositoryService;
        this.passwordMatchValidator = passwordMatchValidator;
    }


    @Transactional(readOnly = true)
    public void changePassword(User user, ChangePasswordRequest request) {

        if (!isPasswordsMatch(user, request)) {
            throw new PasswordDoesNotMatchException("Passwords does not match");
        }

        updateUserPassword(user, request);
        userRepositoryService.save(user);
    }

    private boolean isPasswordsMatch(User user, ChangePasswordRequest request) {
        return passwordMatchValidator.isPasswordsMatch(request.currentPassword(), user.getPassword());
    }

    private void updateUserPassword(User user, ChangePasswordRequest request) {
        user.setPassword(passwordMatchValidator.encodeString(request.newPassword()));
    }
}
