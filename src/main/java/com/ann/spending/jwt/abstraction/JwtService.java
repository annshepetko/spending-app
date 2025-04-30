package com.ann.spending.jwt.abstraction;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String extractUsername(String token);

    Claims extractAllClaims(String token);

    boolean isTokenExpired(String token);
}
