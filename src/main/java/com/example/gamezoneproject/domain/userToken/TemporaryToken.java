package com.example.gamezoneproject.domain.userToken;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;
@Entity
public class TemporaryToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tokenName;
    private String token;

    private LocalDateTime tokenExperienceTime;

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
