package com.ann.spending.jwt.impl;

import com.ann.spending.jwt.abstraction.DefaultJwtManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccessTokenManager extends DefaultJwtManager {


    @Value("${secrets.accessTokenExpiration}")
    private int accessTokenExpiration;

    @Override
    public String generateToken(Map<String, Object> credentials, UserDetails user) {
        return buildToken(credentials, user, this.accessTokenExpiration);
    }


}
