package com.swproject24.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/sign-up", "/check-email", "/check-email-token", "/email-login", "/check-email-login", "/login-link").permitAll()
                        .requestMatchers(HttpMethod.GET, "/profile/*").permitAll() // GET 방식으로 프로필 조회 요청을 허용
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .permitAll()
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }

    @Bean
    public ModelMapper securitymodelMapper() {
        return new ModelMapper();


    }
}
