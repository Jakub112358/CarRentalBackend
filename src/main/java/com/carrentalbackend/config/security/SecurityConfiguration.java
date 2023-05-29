package com.carrentalbackend.config.security;

import com.carrentalbackend.config.ApiConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(ApiConstraints.AUTHENTICATION + "/**").permitAll()
                .requestMatchers(HttpMethod.POST, ApiConstraints.CLIENT).permitAll()
                .requestMatchers(HttpMethod.GET, ApiConstraints.CLIENT + "/{id}").authenticated()
                .requestMatchers(HttpMethod.PATCH, ApiConstraints.CLIENT + "/{id}").authenticated()
                .requestMatchers(HttpMethod.GET, ApiConstraints.COMPANY ).permitAll()
                .requestMatchers(ApiConstraints.CAR_SEARCH).authenticated()
                .requestMatchers(HttpMethod.POST, ApiConstraints.RESERVATION).authenticated()
                .requestMatchers(HttpMethod.GET, ApiConstraints.RESERVATION + "/{id}").authenticated()
                .requestMatchers(HttpMethod.GET, ApiConstraints.RESERVATION).authenticated()
                .anyRequest().hasRole("ADMIN")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
