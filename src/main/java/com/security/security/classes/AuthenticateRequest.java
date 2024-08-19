package com.security.security.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthenticateRequest {
    private String email;
    private String password;


}
