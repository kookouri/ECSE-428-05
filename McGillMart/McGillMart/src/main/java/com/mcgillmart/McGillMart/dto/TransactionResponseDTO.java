package com.mcgillmart.McGillMart.dto;

import java.time.LocalDate;

import com.mcgillmart.McGillMart.model.Transaction;

public class TransactionResponseDTO {
    private int id;
    private double amount;
    private LocalDate dateOfPurchase;
    private String description;

    public TransactionResponseDTO() {}

    public TransactionResponseDTO(Transaction t) {
        id = t.getId();
        amount = t.getAmount();
        dateOfPurchase = t.getDateOfPurchase();
        description = t.getDescription();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
