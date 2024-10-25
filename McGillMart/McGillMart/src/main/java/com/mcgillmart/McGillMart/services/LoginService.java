package com.mcgillmart.McGillMart.services;

import org.springframework.stereotype.Service;

import com.mcgillmart.McGillMart.model.User;

/**
* <p>Service class in charge of logging in users. It implements following use cases: </p>
* <p>Validate login to account</p>
* @author Connor
*/
@Service("loginService")
public class LoginService {
    private UserService userService;
    
    public boolean login(String username, String password) {

        User user = userService.findUserByEmail(username);

        if (username.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (!username.contains("@")) {
            throw new IllegalArgumentException("Email has to contain the character @.");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("The password needs to have 8 characters or more.");
        }
        if (user == null) {
            throw new IllegalArgumentException("User " + username + " not found.");
        }
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password.");
        }
        return true; // Login successful
    }
}
