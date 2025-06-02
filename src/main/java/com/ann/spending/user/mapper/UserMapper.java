package com.ann.spending.user.mapper;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.user.profile.dto.UserView;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserView mapUserToView(User user) {

        return new UserView(user.getUsername(), null, user.getPhoneNumber());
    }
}
