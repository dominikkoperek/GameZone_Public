package com.example.gamezoneproject.domain.user.dto;

import com.example.gamezoneproject.domain.validation.other.notwhitespace.NotWhitespace;
import com.example.gamezoneproject.domain.validation.other.registrationAlphanumeric.AlphanumericAndSpecialSymbols;
import com.example.gamezoneproject.domain.validation.password.Password;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;

public class UserRegistrationDto {
    @NotBlank
    @Size(min = 3,max = 20)
    @AlphanumericAndSpecialSymbols
    @NotWhitespace
    private  String login;
    @NotBlank
    @Size(min = 8,max = 60)
    @NotWhitespace
    @Password
    private  String password;
    @Email
    @NotBlank
    @Size(min = 5,max = 100)
    private String email;
    private Set<Long> tokens;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Long> getTokens() {
        return tokens;
    }

    public void setTokens(Set<Long> tokens) {
        this.tokens = tokens;
    }
}
