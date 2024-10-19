package com.dto;

import com.model.User;
import com.model.ShoppingCart;
import com.model.McGillMart;
import com.model.Transaction;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserListDTO {

    List<UserDTO> users = new ArrayList<>();
    String errorMessage;

    public UserListDTO() {

    }

    public UserListDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static List<UserDTO> userListToUserDTOList(List<User> users) {
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public void setAccounts(List<UserDTO> users) {
        for (UserDTO user : users) {
            this.users.add(user);
        }
    }

    public List<UserDTO> getAccounts() {return users;}

}