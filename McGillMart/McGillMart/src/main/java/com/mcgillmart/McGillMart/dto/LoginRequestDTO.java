package com.mcgillmart.McGillMart.dto;

import com.mcgillmart.McGillMart.model.User;

public class LoginRequestDTO {
    private String username;
    private String password;

    public LoginRequestDTO() {
    }

    // public LoginRequestDTO(User user) { //Create LoginRequestDTO from exisiting User
    //     this.username = user.getUsername();
    //     this.password = user.getPassword();
    // }

    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequestDTO(String errorMessage) {
        this.username = errorMessage;
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
