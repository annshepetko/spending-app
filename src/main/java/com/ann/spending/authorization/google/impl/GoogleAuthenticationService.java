package com.ann.spending.authorization.google.impl;

import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.service.interfaces.AuthenticationResponseBuilder;
import com.ann.spending.authorization.service.interfaces.AuthenticationService;
import com.ann.spending.authorization.service.repository.UserRepositoryService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.stereotype.Service;

@Service
public class GoogleAuthenticationService implements AuthenticationService<GoogleIdToken.Payload> {

    private final AuthenticationResponseBuilder authenticationResponseBuilder;
    private final UserRepositoryService userRepositoryService;


    public GoogleAuthenticationService(AuthenticationResponseBuilder authenticationResponseBuilder, UserRepositoryService userRepositoryService) {
        this.authenticationResponseBuilder = authenticationResponseBuilder;
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public AuthenticationResponse authenticate(GoogleIdToken.Payload request) {

        String email = request.getEmail();
        User user = userRepositoryService.findByEmailIfPresent(email);

        return authenticationResponseBuilder.buildResponse(user);
    }
}
