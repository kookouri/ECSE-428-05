package com.mcgillmart.McGillMart.services;

import org.springframework.stereotype.Service;

import com.mcgillmart.McGillMart.model.User;

import org.springframework.beans.factory.annotation.Autowired;
/**
* <p>Service class in charge of logging in users. It implements following use cases: </p>
* <p>Validate login to account</p>
* @author Connor
*/
@Service("loginService")
public class LoginService {
    @Autowired
    private UserService userService;
    
    public boolean login(String username, String password) {

        if (username.equals("") || password.equals("")) {
            throw new IllegalArgumentException("Please enter both email and password");
        }
        
        User user;
        try {
            user = userService.findUserByEmail(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Email not found, please register");
        }

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password, please try again");
        }
        return true; // Login successful
    }
}
