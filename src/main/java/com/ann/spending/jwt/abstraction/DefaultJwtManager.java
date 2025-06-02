package com.ann.spending.jwt.abstraction;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public abstract class DefaultJwtManager implements JwtTokenGenerator, JwtService {

    @Value("${secrets.jwtSecret}")
    protected String secret;

    @Override
    public abstract String generateToken(Map<String, Object> credentials, UserDetails user);


    @Override
    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            System.out.println("Token expired");
        }

        return false;
    }

    protected String buildToken(Map<String, Object> credentials, UserDetails user, long expiration) {
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


    @Override
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenExpired(String token) {
        try {
            return extractClaim(token, Claims::getExpiration).before(new Date());
        }catch (Exception e){
            return true;
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
}
