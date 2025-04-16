package com.ann.spending.authorization.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {


    @Value("${secrets.accessTokenExpiration}")
    private  int accessTokenExpiration;

    @Value("${secrets.refreshTokenExpiration}")
    private  long refreshToken;

    @Value("${secrets.jwtSecret}")
    private String secret;


    public String generateAccessToken(@Nullable Map<String, Object > credentials, UserDetails user){
        return buildToken(credentials, user, this.accessTokenExpiration);
    }

    public String generateRefreshToken(@Nullable Map<String, Object > credentials, UserDetails user){
        return buildToken(credentials, user, this.refreshToken);
    }

    public Map<String, String> buildTokens(UserDetails user) {
        HashMap<String, String> tokens = new HashMap<>();

        String accessToken = this.generateAccessToken(new HashMap<>(), user);
        String refreshToken = this.generateRefreshToken(new HashMap<>(), user);

        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    private String buildToken(Map<String, Object> credentials, UserDetails user, long expiration) {
        return Jwts.builder()
                .addClaims(credentials)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key generateKey(){
        byte [] secret = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(secret);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
