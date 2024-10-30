package com.mcgillmart.McGillMart.dto;

import java.util.List;

import com.mcgillmart.McGillMart.model.Transaction;
import com.mcgillmart.McGillMart.model.User;

public class UserRequestDTO {
    private int id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private List<ItemResponseDTO> shoppingCart;
    private List<Transaction> history;
    @SuppressWarnings("unused")
    private String errorMessage;

    public UserRequestDTO() {
    }

    public UserRequestDTO(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();

    }

    public UserRequestDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getId()
    {
        return id;
    }

    public String getEmail()
    {
        return email;
    }

    public String getName()
    {
        return name;
    }

    public String getPassword()
    {
        return password;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }


    public List<ItemResponseDTO> getShoppingCart()
    {
        return shoppingCart;
    }

    public List<Transaction> getHistory() { return history;}

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setShoppingCart(List<ItemResponseDTO> shoppingCart)
    {
        this.shoppingCart = shoppingCart;
    }

    public void setHistory( List<Transaction> history) { this.history = history;}
    

}




