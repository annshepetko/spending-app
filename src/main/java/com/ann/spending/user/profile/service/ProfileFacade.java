package com.ann.spending.user.profile.service;


import com.ann.spending.authorization.entity.User;
import com.ann.spending.user.mapper.UserMapper;
import com.ann.spending.user.profile.dto.ProfileView;
import com.ann.spending.user.profile.dto.UserView;
import org.springframework.stereotype.Service;

@Service
public class ProfileFacade {

    private final UserMapper userMapper;


    public ProfileFacade(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public ProfileView retrieveUserProfile(User user) {
        UserView userView = userMapper.mapUserToView(user);

        return new ProfileView(userView);
    }

}
