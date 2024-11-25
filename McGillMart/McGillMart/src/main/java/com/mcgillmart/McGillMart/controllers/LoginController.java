package com.mcgillmart.McGillMart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mcgillmart.McGillMart.dto.LoginRequestDTO;
import com.mcgillmart.McGillMart.services.LoginService;
import com.mcgillmart.McGillMart.services.UserService;


/**
 * <p>Controller class in charge of logging in users. It implements following use cases: </p>
 * <p>Validate login for a user</p>
 * @author Connor
 */
@CrossOrigin(origins = "http://127.0.0.1:8087")
@RestController
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    LoginService loginService;

    // --------------------------// Login //--------------------------//
    @PostMapping(value = { "/login", "/login/" })
    public ResponseEntity<LoginRequestDTO> login(@RequestBody LoginRequestDTO userLogin) {
        try {
            boolean validLogin = loginService.login(userLogin.getUsername(), userLogin.getPassword());
            if (validLogin) {
                return new ResponseEntity<LoginRequestDTO>(new LoginRequestDTO(userLogin.getUsername(), userLogin.getPassword()), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<LoginRequestDTO>(new LoginRequestDTO("Invalid login credentials"), HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<LoginRequestDTO>(new LoginRequestDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
