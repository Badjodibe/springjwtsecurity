package com.security.security.auth;


import lombok.*;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
