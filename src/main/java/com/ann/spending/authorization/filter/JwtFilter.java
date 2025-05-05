package com.ann.spending.authorization.filter;

import com.ann.spending.authorization.cookie.facrory.CookieFactory;
import com.ann.spending.authorization.cookie.impl.CookieAuthFactory;
import com.ann.spending.jwt.abstraction.JwtService;
import com.ann.spending.jwt.abstraction.JwtTokenGenerator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;
    private final JwtTokenGenerator jwtTokenGenerator;

    public JwtFilter(
            UserDetailsService userDetailsService,
            @Qualifier("accessTokenManager") JwtService jwtService,
            @Qualifier("accessTokenManager") JwtTokenGenerator jwtTokenGenerator
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        final String tokenHeader = request.getHeader("Authorization");
        final String accessToken;
        final String refreshToken;

        final Cookie[] incomingCookies = request.getCookies();

        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        accessToken = tokenHeader.substring(7);
        refreshToken = findCookieByName(incomingCookies, "Refresh-Token")
                .orElseThrow()
                .getValue();

        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            boolean isAccessTokenExpired = jwtService.isTokenExpired(accessToken);
            if (isAccessTokenExpired) {

                if (jwtService.isTokenExpired(refreshToken)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "refresh token is expired");
                    return;
                }
                UserDetails userDetails = performAuthorization(request, refreshToken);

                refreshAccessToken(response, userDetails);

                setRequestAttributes(request, userDetails);
                filterChain.doFilter(request, response);
                return;

            }

            UserDetails userDetails = performAuthorization(request, accessToken);

            setRequestAttributes(request, userDetails);
            filterChain.doFilter(request, response);

            return;
        }
        filterChain.doFilter(request, response);

    }

    private void refreshAccessToken(HttpServletResponse response, UserDetails userDetails) {
        String newAccessToken = jwtTokenGenerator.generateToken(new HashMap<>(), userDetails);
        response.addCookie(new Cookie("Access-Token", newAccessToken));
    }

    private static void setRequestAttributes(HttpServletRequest request, UserDetails userDetails) {
        request.setAttribute("username", userDetails.getUsername());
        request.setAttribute("user", userDetails);
    }

    private UserDetails performAuthorization(HttpServletRequest request, String refreshToken) {

        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        setAuthenticationContext(userDetails, request);

        return userDetails;
    }


    private void setAuthenticationContext(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


    }

    private Optional<Cookie> findCookieByName(Cookie[] cookies, String name) {
        return Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals(name)).findFirst();
    }
}
