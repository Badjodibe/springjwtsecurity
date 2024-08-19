package com.security.security.configuration;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider autheticationProvider;

    public SecurityConfiguration(JWTAuthenticationFilter jwtAuthFilter, AuthenticationProvider autheticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.autheticationProvider = autheticationProvider;
    }
    /*
    * The security of the application is configured here
    * First the csrf is configured
    * What kind of http requests is authorized
    * The session management
    * authentication provider
    *
    * */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    ) throws Exception{
       return httpSecurity
               .csrf(
                       AbstractHttpConfigurer::disable
               )
               .sessionManagement(
                       sessionManagement -> sessionManagement
                               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               )
               .authenticationProvider(
                       autheticationProvider
               )
               .addFilterBefore(
                       jwtAuthFilter,
                       UsernamePasswordAuthenticationFilter.class
               )
               .build();
    }

}
