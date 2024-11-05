package com.SpringDemo.SpringSecurity.Dto;

import jakarta.persistence.Column;

public class LoginDto {

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
