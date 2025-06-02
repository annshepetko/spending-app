package com.ann.spending.authorization.service.impl.authentication;

import com.ann.spending.authorization.dto.AuthenticationRequest;
import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.service.interfaces.AuthenticationResponseBuilder;
import com.ann.spending.authorization.service.interfaces.AuthenticationService;
import com.ann.spending.authorization.service.repository.UserRepositoryService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultAuthenticationServiceImpl implements AuthenticationService<AuthenticationRequest> {

    private final UserRepositoryService userRepositoryService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationResponseBuilder authResponseBuilder;

    public DefaultAuthenticationServiceImpl(
            UserRepositoryService userRepositoryService,
            AuthenticationManager authenticationManager,
            AuthenticationResponseBuilder authResponseBuilder) {
        this.userRepositoryService = userRepositoryService;
        this.authenticationManager = authenticationManager;
        this.authResponseBuilder = authResponseBuilder;

    }


    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticateUser(authenticationRequest);
        User user = userRepositoryService.findByEmailIfPresent(authentication.getName());

        return authResponseBuilder.buildResponse(user) ;
    }

    private Authentication authenticateUser(AuthenticationRequest authenticationRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.email(),
                        authenticationRequest.password()
                )
        );
    }

}
