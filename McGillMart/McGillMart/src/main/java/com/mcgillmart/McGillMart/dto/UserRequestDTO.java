package com.mcgillmart.McGillMart.dto;

import java.util.List;

import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.model.Transaction;
import com.mcgillmart.McGillMart.model.User;

public class UserRequestDTO {
    private int id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private List<Item> shoppingCart;
    private List<Transaction> history;
    private McGillMart mcGillMart;
    private String errorMessage;

    public UserRequestDTO() {
    }

    public UserRequestDTO(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.shoppingCart = user.getShoppingCart();
        this.history = user.getHistory();
        this.mcGillMart = user.getMcGillMart();
        this.id = user.getId();
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


    public List<Item> getShoppingCart()
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

    public void setShoppingCart(List<Item> shoppingCart)
    {
        this.shoppingCart = shoppingCart;
    }

    public void setHistory( List<Transaction> history) { this.history = history;}


}



