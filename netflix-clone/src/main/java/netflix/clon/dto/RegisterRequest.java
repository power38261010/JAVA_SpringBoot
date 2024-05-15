package com.netflix.clon.dto;

public class RegisterRequest {
    private String username;
    private String password;

    public RegisterRequest() {
        // Constructor vacío necesario para deserialización JSON
    }

    public RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
