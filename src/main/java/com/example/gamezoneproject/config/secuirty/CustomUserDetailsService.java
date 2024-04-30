package com.example.gamezoneproject.config.secuirty;

import com.example.gamezoneproject.domain.user.UserService;
import com.example.gamezoneproject.domain.user.dto.UserCredentialsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findCredentialsByLogin(username)
                .map(this::createUserDetails)
                .orElseThrow(()->new UsernameNotFoundException(String.format("User with email %s not found",username)));
    }

    private UserDetails createUserDetails(UserCredentialsDto userCredentials) {
        return User.builder()
                .username(userCredentials.getLogin())
                .password(userCredentials.getPassword())
                .roles(userCredentials.getRoles().toArray(String[]::new))
                .disabled(!userCredentials.isActive())
                .build();
    }



}
