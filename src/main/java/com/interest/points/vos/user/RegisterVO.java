package com.interest.points.vo.user;

import com.interest.points.model.UserRole;
import jakarta.validation.constraints.NotBlank;

public class RegisterVO {
    @NotBlank
    String username;
    @NotBlank
    String fullName;
    @NotBlank
    String password;
    UserRole role;

    public RegisterVO() {
    }

    public RegisterVO(String username, String fullName, String password, UserRole role) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public @NotBlank String getFullName() {
        return fullName;
    }

    public void setFullName(@NotBlank String fullName) {
        this.fullName = fullName;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
