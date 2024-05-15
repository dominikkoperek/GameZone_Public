package com.example.gamezoneproject.domain.userToken.dto;

import java.time.LocalDateTime;

public class TemporaryTokenDto {
    private Long id;
    private String tokenName;
    private String token;

    private LocalDateTime tokenExperienceTime;
    private LocalDateTime lastTokenSend;

    public TemporaryTokenDto(String tokenName, String token, LocalDateTime tokenExperienceTime, LocalDateTime lastTokenSend) {
        this.tokenName = tokenName;
        this.token = token;
        this.tokenExperienceTime = tokenExperienceTime;
        this.lastTokenSend = lastTokenSend;
    }

    public TemporaryTokenDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTokenExperienceTime() {
        return tokenExperienceTime;
    }

    public void setTokenExperienceTime(LocalDateTime tokenExperienceTime) {
        this.tokenExperienceTime = tokenExperienceTime;
    }

    public LocalDateTime getLastTokenSend() {
        return lastTokenSend;
    }

    public void setLastTokenSend(LocalDateTime lastTokenSend) {
        this.lastTokenSend = lastTokenSend;
    }
}
