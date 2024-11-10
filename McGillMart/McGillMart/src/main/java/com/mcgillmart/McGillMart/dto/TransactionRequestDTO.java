package com.mcgillmart.McGillMart.dto;

import java.time.LocalDate;

public class TransactionRequestDTO {
    private double amount;
    private LocalDate dateOfPurchase;
    private String description;
    private UserResponseDTO user;

    public TransactionRequestDTO() {}

    public TransactionRequestDTO(double aAmount, LocalDate aDateOfPurchase, UserResponseDTO aUser) {
        amount = aAmount;
        dateOfPurchase = aDateOfPurchase;
        user = aUser;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }
}
