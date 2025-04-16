package com.ann.spending.authorization.filter;

import com.ann.spending.authorization.jwt.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        final String tokenHeader = request.getHeader("Authorization");
        final String accessToken;

        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        accessToken = tokenHeader.substring(7);
        try {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                boolean isTokenExpired = jwtService.isTokenExpired(accessToken);
                if (!isTokenExpired){

                    String username = jwtService.extractUsername(accessToken);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    setAuthenticationContext(userDetails, request);

                    request.setAttribute("username", userDetails.getUsername());
                    request.setAttribute("user", userDetails);
                }

            }
            filterChain.doFilter(request, response);

        }catch (JwtException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expired");

        }

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
}
