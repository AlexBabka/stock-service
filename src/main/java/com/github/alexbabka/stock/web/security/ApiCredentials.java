package com.github.alexbabka.stock.web.security;

/**
 * Pojo class represents user of the Rest Api.
 */
public class ApiCredentials {
    private String username;
    private String password;

    public ApiCredentials() {
    }

    public ApiCredentials(String username, String password) {
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
