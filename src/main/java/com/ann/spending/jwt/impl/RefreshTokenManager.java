package com.ann.spending.jwt.impl;

import com.ann.spending.jwt.abstraction.DefaultJwtManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RefreshTokenManager extends DefaultJwtManager {

    @Value("${secrets.refreshTokenExpiration}")
    private  long refreshToken;

    @Override
    public String generateToken(Map<String, Object> credentials, UserDetails user) {

        return buildToken(credentials, user, this.refreshToken);
    }
}
