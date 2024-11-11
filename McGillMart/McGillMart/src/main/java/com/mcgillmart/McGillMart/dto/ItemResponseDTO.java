package com.mcgillmart.McGillMart.dto;

import java.util.List;
import java.util.stream.Collectors;


import com.mcgillmart.McGillMart.model.Item;

public class ItemResponseDTO {
    private int id;
    private String name;
    private double price;
    private String description;
    private String category;
    private int reviewCount;
    private String url;
    private List<ReviewResponseDTO> reviews;

    public ItemResponseDTO() {
    }

    public ItemResponseDTO(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.description = item.getDescription();
        this.category = item.getCategory().toString();
        this.url = item.getUrl();
        this.reviewCount = item.getReviews().size();
        this.reviews = item.getReviews().stream().map(ReviewResponseDTO::new).collect(Collectors.toList());
    }

    public ItemResponseDTO(String errorMessage) {
        this.description = errorMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
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

    public static List<ItemResponseDTO> itemListToItemResponseDTOList(List<Item> items) {
        return items.stream().map(ItemResponseDTO::new).collect(Collectors.toList());
    }
}
