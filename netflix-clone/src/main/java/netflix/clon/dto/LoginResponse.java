package com.netflix.clon.dto;

public class LoginResponse {
    private String token;

    public LoginResponse() {
        // Constructor vacío necesario para deserialización JSON
    }

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
