package com.ann.spending.jwt.abstraction;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtTokenGenerator {
    String generateToken(Map<String, Object > credentials, UserDetails user);
}
