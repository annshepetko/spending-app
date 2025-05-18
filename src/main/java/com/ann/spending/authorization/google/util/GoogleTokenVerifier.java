package com.ann.spending.authorization.google.util;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class GoogleTokenVerifier {

    private static final String CLIENT_ID = "YOUR_CLIENT_ID.apps.googleusercontent.com";

    public GoogleIdToken.Payload verifyToken(String idTokenString) throws Exception {

        GoogleIdTokenVerifier verifier = buildTokenVerifier();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        return handlePayload(idToken);
    }

    private static GoogleIdTokenVerifier buildTokenVerifier() {
        return new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
    }

    private static GoogleIdToken.Payload handlePayload(GoogleIdToken idToken) {
        if (idToken != null) {
            return idToken.getPayload();
        } else {
            throw new IllegalArgumentException("Invalid ID token.");
        }
    }
}
