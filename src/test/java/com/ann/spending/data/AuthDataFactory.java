package com.ann.spending.data;

import com.ann.spending.authorization.dto.AuthenticationRequest;
import com.ann.spending.authorization.dto.RegistrationRequest;
import com.ann.spending.authorization.entity.User;

public class AuthDataFactory {

    public static RegistrationRequest createRegistrationRequest() {
        return new RegistrationRequest(
                "test@example.com",
                "ann",
                null,
                "raw-password"
        );
    }

    public static AuthenticationRequest createAuthenticationRequest() {
        return new AuthenticationRequest("test@example.com", "raw-password");
    }


    public static User createUser() {
        User user = new User("test@example.com", "encoded-password");

        user.setName("ann");
        return user;
    }


}
