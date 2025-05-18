package com.ann.spending.authorization.google.service;

import com.ann.spending.authorization.dto.GoogleAuthToken;
import com.ann.spending.authorization.google.util.GoogleTokenVerifier;
import com.ann.spending.exception.google.InvalidGoogleToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.stereotype.Component;

@Component
public class GoogleTokenFilter {


    private final GoogleTokenVerifier googleTokenVerifier;

    public GoogleTokenFilter(GoogleTokenVerifier googleTokenVerifier) {
        this.googleTokenVerifier = googleTokenVerifier;
    }

    public GoogleIdToken.Payload handlePayload(GoogleAuthToken googleAuthToken) {

        GoogleIdToken.Payload payload = tryToHandleGoogleCreds(googleAuthToken);

        validatePayloadFields(payload);
        return payload;
    }

    private GoogleIdToken.Payload tryToHandleGoogleCreds(GoogleAuthToken googleAuthToken) {
        try {

            return googleTokenVerifier.verifyToken(googleAuthToken.token());

        } catch (Exception e) {
            throw new InvalidGoogleToken("Error has occurred during handling auth credentials");
        }
    }


    private static void validatePayloadFields(GoogleIdToken.Payload payload) {

        if (payload.isEmpty() || !payload.getEmailVerified()) {
            throw new InvalidGoogleToken("Error has occurred during handling auth credentials");
        }
    }
}
