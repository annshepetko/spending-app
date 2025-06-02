package com.ann.spending.user.controller;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.user.dto.ChangePasswordRequest;
import com.ann.spending.user.service.ChangePasswordService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/user/password")
public class UserPasswordController {

    private final ChangePasswordService changePasswordService;

    public UserPasswordController(ChangePasswordService changePasswordService) {
        this.changePasswordService = changePasswordService;
    }

    @PatchMapping
    public void changePassword(@RequestAttribute("user") User user, @RequestBody ChangePasswordRequest changePasswordRequest) {

        changePasswordService.changePassword(user, changePasswordRequest);
    }


}
