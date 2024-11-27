package com.mcgillmart.McGillMart.dto;

import java.time.LocalDate;

public class ReviewRequestDTO {
    private String email;
    private String phoneNumber;
    private String password;
    private int rating;
    private String comment;
    private LocalDate datePosted;
    private String username;
    private String itemName;

    // Default Constructor
    public ReviewRequestDTO() {}

    // Parameterized Constructor
    public ReviewRequestDTO(String email, String phoneNumber, String password, int rating, String comment, 
                            LocalDate datePosted, String username, String itemName) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.rating = rating;
        this.comment = comment;
        this.datePosted = datePosted;
        this.username = username;
        this.itemName = itemName;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
