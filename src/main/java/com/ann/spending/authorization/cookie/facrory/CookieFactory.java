package com.ann.spending.authorization.cookie.facrory;

import jakarta.servlet.http.Cookie;

public interface CookieFactory <V>{

    Cookie createCookie(V value);
}
