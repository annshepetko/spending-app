package com.ann.spending.authorization.cookie.impl;

import com.ann.spending.authorization.cookie.facrory.CookieFactory;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieAuthFactory implements CookieFactory<String> {

    @Override
    public Cookie createCookie(String token) {
        Cookie cookie = new Cookie("Refresh-Token", token);
        cookie.setMaxAge(2592000);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(false);

        return cookie;
    }
}
