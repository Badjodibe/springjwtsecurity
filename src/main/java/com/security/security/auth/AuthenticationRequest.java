package com.security.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class  AuthenticationRequest {
    private String email;
    private String password;
}
