package com.ann.spending.user.profile.controller;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.user.UserCrudService;
import com.ann.spending.user.dto.ChangePasswordRequest;
import com.ann.spending.user.profile.dto.ProfileView;
import com.ann.spending.user.profile.dto.UserView;
import com.ann.spending.user.profile.service.ProfileFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/profile")
public class UserProfileController {

    private final ProfileFacade profileFacade;


    public UserProfileController(ProfileFacade profileFacade) {
        this.profileFacade = profileFacade;
    }

    @GetMapping
    public ResponseEntity<ProfileView> retrieveUserProfile(@RequestAttribute("user") User user) {

        return new ResponseEntity<>(profileFacade.retrieveUserProfile(user), HttpStatus.OK);
    }

}
