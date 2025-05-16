package com.ann.spending.authorization.service.impl.builder;

import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.service.interfaces.AuthenticationResponseBuilder;
import com.ann.spending.jwt.abstraction.JwtTokenGenerator;
import com.ann.spending.jwt.impl.RefreshTokenManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthResponseBuilderImpl implements AuthenticationResponseBuilder {

    private final JwtTokenGenerator accessTokenService;

    public AuthResponseBuilderImpl(
            @Qualifier("accessTokenManager") JwtTokenGenerator accessTokenService,
            @Qualifier("refreshTokenManager") JwtTokenGenerator refreshTokenService
    ) {
        this.accessTokenService = accessTokenService;
        this.refreshTokenService = refreshTokenService;
    }

    private final JwtTokenGenerator refreshTokenService;

    @Override
    public AuthenticationResponse buildResponse(User user) {

        String jwtToken = accessTokenService.generateToken(new HashMap<>(), user);
        String refreshToken = refreshTokenService.generateToken(new HashMap<>(), user);

        return new AuthenticationResponse(jwtToken, "HELLO FROM PIPELINE", refreshToken);
    }
}
