package com.security.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider autheticationProvider;
    private  final CustomAccessDeniedHandler accessDeniedHandler;
    public SecurityConfiguration(
            JWTAuthenticationFilter jwtAuthFilter, AuthenticationProvider autheticationProvider, CustomAccessDeniedHandler accessDeniedHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.autheticationProvider = autheticationProvider;
        this.accessDeniedHandler = accessDeniedHandler;
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
               .cors(
                       AbstractHttpConfigurer::disable
               )
               .authorizeHttpRequests(
                       http -> http
                               .requestMatchers("/api/v1/auth/**")
                               .permitAll()
                               .anyRequest()
                               .authenticated()
               )
               .exceptionHandling(
                       e -> e.accessDeniedHandler(accessDeniedHandler)
                               .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.FORBIDDEN))
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
