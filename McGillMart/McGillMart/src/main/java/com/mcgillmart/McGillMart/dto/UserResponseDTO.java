package com.mcgillmart.McGillMart.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.model.ShoppingCart;
import com.mcgillmart.McGillMart.model.Transaction;
import com.mcgillmart.McGillMart.model.User;

public class UserResponseDTO {
    private int id;
    private String email;
    private String name;
    private String phoneNumber;
    private ShoppingCart shoppingCart;
    private List<Transaction> history;
    private McGillMart mcGillMart;
    private String errorMessage;

    public UserResponseDTO() {
    }

    public UserResponseDTO(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.shoppingCart = user.getShoppingCart();
        this.history = user.getHistory();
        this.mcGillMart = user.getMcGillMart();
        this.id = user.getId();
    }

    public UserResponseDTO(String errorMessage) {
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

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public ShoppingCart getShoppingCart()
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

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setShoppingCart(ShoppingCart shoppingCart)
    {
        this.shoppingCart = shoppingCart;
    }

    public void setHistory( List<Transaction> history) { this.history = history;}

}