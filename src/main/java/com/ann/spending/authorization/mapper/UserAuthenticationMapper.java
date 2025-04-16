package com.ann.spending.authorization.mapper;

import com.ann.spending.authorization.dto.RegistrationRequest;
import com.ann.spending.authorization.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationMapper {

    public User mapToUser(RegistrationRequest registrationRequest){

        User user = User.builder()
                .password(registrationRequest.password())
                .email(registrationRequest.email())
                .name(registrationRequest.username())
                .phoneNumber(registrationRequest.phoneNumber())
                .build();

        return user;
    }
}
