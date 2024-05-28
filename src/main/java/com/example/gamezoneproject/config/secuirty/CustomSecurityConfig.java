package com.example.gamezoneproject.config.secuirty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class CustomSecurityConfig {
    private final static String USER_ROLE = "USER";
    private final static String MODERATOR_ROLE = "MODERATOR";
    private final static String ADMIN_ROLE = "ADMIN";

    @Bean
    public SecurityFilterChain filterChain(@Value("${remember-me.token}") String token, HttpSecurity http ) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/admin/dodaj-platforme").hasAnyRole(ADMIN_ROLE)
                        .requestMatchers("/admin/**").hasAnyRole(ADMIN_ROLE, MODERATOR_ROLE)
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/zaloguj")
                        .failureUrl("/zaloguj?blad")
                        .permitAll())
                .rememberMe(rememberMeConfigurer->rememberMeConfigurer
                        .key(token)
                        .tokenValiditySeconds(86_400))

                .logout((logout) -> logout
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                        .logoutUrl("/wyloguj")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/wyloguj/**", HttpMethod.GET.name()))
                        .logoutSuccessUrl("/?wyloguj").permitAll()
                        .permitAll()
                );

        http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()));
        http.headers(config -> config.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
