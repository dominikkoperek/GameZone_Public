package com.example.gamezoneproject.domain.user.dto;

public class UserTokenDto {
    private Long id;
    private String userName;
    private String token;
    private String email;

    public UserTokenDto(Long id, String userName, String token, String email) {
        this.id = id;
        this.userName = userName;
        this.token = token;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
