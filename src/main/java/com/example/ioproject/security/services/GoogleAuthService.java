package com.example.ioproject.security.services;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GoogleAuthService {

    private final GoogleIdTokenVerifier verifier;

    public GoogleAuthService() {
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("61322257566-9elqgssfuo4id97ej8t4sfjbt1egnqq0.apps.googleusercontent.com"))
                .build();
    }

    public IdToken.Payload verify(String frontendTokenString) {
        try {
            GoogleIdToken idToken = verifier.verify(frontendTokenString);
            if (idToken != null) {
                return idToken.getPayload();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
