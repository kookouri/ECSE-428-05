package com.mcgillmart.McGillMart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.repositories.UserRepository;

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
    // @Autowired
    // private UserRepository userRepository;
    
    public boolean login(String email, String password) {

        if (email.equals("") || password.equals("")) {
            throw new IllegalArgumentException("Please enter both email and password");
        }
        else if (!email.contains("@")) {
            throw new IllegalArgumentException("Email has to contain the character @.");
        }
        else if (password.length() < 8) {
            throw new IllegalArgumentException("The password needs to have 8 characters or more.");
        }

        User user;
        try {
            user = userService.findUserByEmail(email);
        } catch (Exception e) {
            throw new IllegalArgumentException("Email not found, please register");
        }

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password, please try again");
        }
        return true; // Login successful
    }
}
