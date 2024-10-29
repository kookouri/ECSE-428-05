package com.mcgillmart.McGillMart.dto;

import com.mcgillmart.McGillMart.model.Review;

import java.time.LocalDate;

public class ReviewRequestDTO {
    private int rating;
    private String comment;
    private LocalDate datePosted;
    private String username;
    private String itemName;

    public ReviewRequestDTO() {}

    public ReviewRequestDTO(int rating, String comment, LocalDate datePosted, String username, String itemName) {
        this.rating = rating;
        this.comment = comment;
        this.datePosted = datePosted;
        this.username = username;
        this.itemName = itemName;
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

    public void setDatePosted(LocalDate  datePosted) {
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
