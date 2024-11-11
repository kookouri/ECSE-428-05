package com.mcgillmart.McGillMart.dto;

import java.util.List;
import java.util.stream.Collectors;


import com.mcgillmart.McGillMart.model.Item;

public class ItemRequestDTO {
    private String name;
    private double price;
    private String description;
    private String category;
    private String url;
    private int reviewCount;
    private List<ReviewResponseDTO> reviews;

    public ItemRequestDTO() {
    }

    public ItemRequestDTO(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.description = item.getDescription();
        this.category = item.getCategory().toString();
        this.reviewCount = item.getReviews().size();
        this.url = item.getUrl();
        this.reviews = item.getReviews().stream().map(ReviewResponseDTO::new).collect(Collectors.toList());
    }

    public ItemRequestDTO(String errorMessage) {
        this.description = errorMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<ReviewResponseDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewResponseDTO> reviews) {
        this.reviews = reviews;
    }

    public static List<ItemRequestDTO> itemListToItemRequestDTOList(List<Item> items) {
        return items.stream().map(ItemRequestDTO::new).collect(Collectors.toList());
    }
}
