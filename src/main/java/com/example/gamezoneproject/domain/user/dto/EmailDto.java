package com.example.gamezoneproject.domain.user.dto;

import com.example.gamezoneproject.validation.other.email.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmailDto {
    @Email
    @NotBlank
    @Size(min = 5,max = 100)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
