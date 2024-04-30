package com.example.gamezoneproject.domain.user.dto;

import java.util.Set;

public class UserCredentialsDto {
    private final String login;
    private final String password;
    private final Set<String> roles;
    private final boolean isActive;

    public UserCredentialsDto(String login, String password, Set<String> roles, boolean isActive) {
        this.login = login;
        this.password = password;
        this.roles = roles;
        this.isActive = isActive;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public boolean isActive() {
        return isActive;
    }
}
