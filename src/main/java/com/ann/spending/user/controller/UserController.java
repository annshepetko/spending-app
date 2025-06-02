package com.ann.spending.user.controller;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.user.UserCrudService;
import com.ann.spending.user.profile.dto.UserView;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    private final UserCrudService userCrudService;

    public UserController(UserCrudService userCrudService) {
        this.userCrudService = userCrudService;
    }

    @PatchMapping
    public void updateUser(@RequestBody UserView userView, @RequestAttribute("user") User user) {
        userCrudService.updateUser(userView, user);
    }

}
