package com.example.gamezoneproject.domain.user.dto;

import com.example.gamezoneproject.domain.validation.other.notwhitespace.NotWhitespace;
import com.example.gamezoneproject.domain.validation.password.FieldEqualField2;
import com.example.gamezoneproject.domain.validation.password.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@FieldEqualField2(
        field1 = "password",
        field2 = "confirmPassword")
public class UserResetPasswordDto {

    @NotBlank
    @Size(min = 8,max = 60)
    @NotWhitespace
    @Password
    private String password;
    @NotBlank
    @Size(min = 8,max = 60)
    @NotWhitespace
    @Password
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
