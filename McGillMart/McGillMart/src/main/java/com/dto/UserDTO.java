package com.dto;

import com.model.User;
import com.model.ShoppingCart;
import com.model.McGillMart;
import com.model.Transaction;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private int id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private ShoppingCart shoppingCart;
    private List<Transaction> history;
    private McGillMart mcGillMart;
    private String errorMessage;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.shoppingCart = user.getShoppingCart();
        this.history = user.getHistory();
        this.mcGillMart = user.getMcGillMart();
        this.id = user.getId();
    }

    public UserDTO(String errorMessage) {
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

    public void setPassword(String password)
    {
        this.password = password;
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



