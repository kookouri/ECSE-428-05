package com.mcgillmart.McGillMart.dto;

import com.mcgillmart.McGillMart.model.Review;

import java.time.LocalDate;

public class ReviewResponseDTO {
    private int id;
    private int rating;
    private String comment;
    private LocalDate datePosted;
    private String username;
    private String itemName;

    public ReviewResponseDTO() {}

    public ReviewResponseDTO(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.datePosted = review.getDatePosted();
        this.username = review.getUsername();
        this.itemName = review.getItem().getName(); 
    }

    public ReviewResponseDTO(String errorMessage) {
        this.comment = errorMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
