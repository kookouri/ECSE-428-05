package com.mcgillmart.McGillMart.controllers;

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

import jakarta.servlet.http.HttpServletRequest;

import com.mcgillmart.McGillMart.dto.UserListDTO;
import com.mcgillmart.McGillMart.dto.UserRequestDTO;
import com.mcgillmart.McGillMart.dto.UserResponseDTO;
import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.services.UserService;
import com.mcgillmart.McGillMart.services.ViewProfileService;

/**
 * <p>Controller class in charge of managing users. It implements following use cases: </p>
 * <p>Create, update, read and delete a user </p>
 * @author Julia
 */
@CrossOrigin(origins = "*")
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ViewProfileService viewAccountService;

    //--------------------------// Create User //--------------------------//
    @PostMapping(value={"/users", "/users/", "/public/users"})
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO user) {
        try {
            User createdUser = userService.createUser(user.getEmail(), user.getName(), user.getPassword(), user.getPhoneNumber());
            return new ResponseEntity<UserResponseDTO>(new UserResponseDTO(createdUser), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<UserResponseDTO>(new UserResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    //--------------------------// Update Account //--------------------------//
    /* 
    @PutMapping(value = {"/users/{id}", "/users/{id}/"})
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") Integer id, @RequestBody UserRequestDTO account) {
    try {
        User updatedAccount = userService.updateUser(id, account.getEmail(), account.getName(), account.getPassword(), account.getPhoneNumber());
        return new ResponseEntity<>(new UserResponseDTO(updatedAccount), HttpStatus.ACCEPTED);
    } catch (IllegalArgumentException e) {
        return new ResponseEntity<>(new UserResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
} */
    @PutMapping(value = {"/users/{id}", "/users/{id}/"})
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer id, @RequestBody UserRequestDTO account) {
        try {
            System.out.println("Received payload: " + account);
            User updatedUser = userService.updateUser(id, account.getEmail(), account.getName(), account.getPassword(), account.getPhoneNumber());
            return ResponseEntity.ok(new UserResponseDTO(updatedUser));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
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
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<UserResponseDTO>(new UserResponseDTO(userService.findUserById(id)), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<UserResponseDTO>(new UserResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    // Can either get all user with /users
    // Or get a specific user with its email /users?email=julia@mail.com
    @GetMapping(value={"/users", "/users/", "/users/account", "/users/account/"})
    public ResponseEntity<UserListDTO> findAllUsers(@RequestParam(name = "email", required = false) String email, HttpServletRequest request) {
        try {
            UserListDTO userList = new UserListDTO();

            if (email!=null) {
                List<User> list = new ArrayList<>();
                if (request.getRequestURI().contains("/account")) {
                	list.add(viewAccountService.viewUserProfile(email));
                } else {
                	list.add(userService.findUserByEmail(email));
                }
                userList.setAccounts(UserListDTO.userListToUserResponseDTOList(list));
            }
            else {
                userList.setAccounts(UserListDTO.userListToUserResponseDTOList(userService.findAllUsers()));
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