package com.example.gamezoneproject.domain.userToken.dto;

import com.example.gamezoneproject.domain.userToken.TemporaryTokenNames;

import java.time.LocalDateTime;

public class TemporaryTokenDto {
    private Long id;
    private TemporaryTokenNames tokenName;
    private String token;

    private LocalDateTime tokenExperienceTime;

    public TemporaryTokenDto( TemporaryTokenNames tokenName, String token, LocalDateTime tokenExperienceTime) {
        this.tokenName = tokenName;
        this.token = token;
        this.tokenExperienceTime = tokenExperienceTime;
    }

    public TemporaryTokenDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TemporaryTokenNames getTokenName() {
        return tokenName;
    }

    public void setTokenName(TemporaryTokenNames tokenName) {
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
}
