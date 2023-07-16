package com.gamma.auth;

import java.util.List;

public class LoginResponse {

    private String username;

    private String token;

    private List<String> authorities;

    public LoginResponse(String username, String token, List<String> authorities) {
        this.username = username;
        this.token = token;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
