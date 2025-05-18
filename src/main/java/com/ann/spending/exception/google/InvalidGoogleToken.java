package com.ann.spending.exception.google;

public class InvalidGoogleToken extends RuntimeException {
    public InvalidGoogleToken(String message) {
        super(message);
    }
}
