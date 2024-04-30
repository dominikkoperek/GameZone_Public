package com.example.gamezoneproject.domain.user.dto;

public class UserTokenDto {
    private Long id;
    private String userName;
    private String token;

    public UserTokenDto(Long id, String userName, String token) {
        this.id = id;
        this.userName = userName;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
