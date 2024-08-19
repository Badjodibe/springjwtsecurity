package com.security.security.auth;


import lombok.*;

@Data
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
}
