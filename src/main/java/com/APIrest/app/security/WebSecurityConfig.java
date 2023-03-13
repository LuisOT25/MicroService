package com.APIrest.app.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSec) throws Exception {
        LOGGER.error("security()");
        httpSec.csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/v3/api-docs/**").permitAll()
                .antMatchers(HttpMethod.GET,"/swagger-ui/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/login").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/token").permitAll()
                .anyRequest().authenticated();
        return httpSec.build();
    }


}
