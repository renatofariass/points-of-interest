package com.interest.points.vo.user;

import jakarta.validation.constraints.NotBlank;

public class AuthVO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public AuthVO() {
    }

    public AuthVO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }
}
