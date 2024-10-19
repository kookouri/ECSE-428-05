package com.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.User;
import com.services.UserService;

import com.dto.UserDTO;
import com.dto.UserListDTO;

/**
 * <p>Controller class in charge of managing users. It implements following use cases: </p>
 * <p>Create, update, read and delete a user </p>
 * @author Julia
*/
@CrossOrigin(origins = "http://127.0.0.1:8087")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    //--------------------------// Create User //--------------------------//
    @PostMapping(value={"/users", "/users/", "/public/users"})
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        try {
            User createdUser = userService.createUser(user.getEmail(), user.getName(), user.getPassword(), user.getPhoneNumber());
            return new ResponseEntity<UserDTO>(new UserDTO(createdUser), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<UserDTO>(new UserDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    
    //--------------------------// Update Account //--------------------------//

    @PutMapping(value={"/users/{id}", "/users/{id}/"})
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO account) {
        try {
            User updatedAccount = userService.updateUser(id, account.getEmail(),  account.getName(), account.getPassword(), account.getPhoneNumber());
            return new ResponseEntity<UserDTO>(new UserDTO(updatedAccount), HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<UserDTO>(new UserDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    //--------------------------// Delete Account //--------------------------//

    @DeleteMapping(value={"/users/{id}", "/users/{id}/"})
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<String>("Deleted", HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    //--------------------------// Getters //--------------------------//


    @GetMapping(value={"/users/{id}", "/users/{id}/"})
    public ResponseEntity<UserDTO> findUserById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<UserDTO>(new UserDTO(userService.findUserById(id)), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<UserDTO>(new UserDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    // Can either get all user with /users
    // Or get a specific user with its email /users?email=julia@mail.com
    @GetMapping(value={"/users", "/users/"})
    public ResponseEntity<UserListDTO> findAllUsers(@RequestParam(name = "email", required = false) String email) {
        try {
            UserListDTO userList = new UserListDTO();

            if (email!=null) {
                List<User> list = new ArrayList<>();
                list.add(userService.findUserByEmail(email));
                userList.setAccounts(UserListDTO.userListToUserDTOList(list));
            }
            else {
                userList.setAccounts(UserListDTO.userListToUserDTOList(userService.findAllUsers()));
            }

            if (userList.getAccounts().size() > 0)
                return new ResponseEntity<UserListDTO>(userList, HttpStatus.OK);
            else
                return new ResponseEntity<UserListDTO>(userList, HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<UserListDTO>(new UserListDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}