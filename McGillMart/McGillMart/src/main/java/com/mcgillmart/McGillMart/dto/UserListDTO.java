package com.dto;

import com.model.User;
import com.model.ShoppingCart;
import com.model.McGillMart;
import com.model.Transaction;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserListDTO {

    List<UserResponseDTO> users = new ArrayList<>();
    String errorMessage;

    public UserListDTO() {

    }

    public UserListDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static List<UserResponseDTO> userListToUserResponseDTOList(List<User> users) {
        return users.stream().map(UserResponseDTO::new).collect(Collectors.toList());
    }

    public void setAccounts(List<UserResponseDTO> users) {
        for (UserResponseDTO user : users) {
            this.users.add(user);
        }
    }

    public List<UserResponseDTO> getAccounts() {return users;}

}