package com.dto;

import com.model.Item;
import com.model.McGillMart;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDTO {
    private String name;
    private double price;
    private String description;
    private String category;
    private McGillMart mcGillMart;

    public ItemDTO() {
    }

    public ItemDTO(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.description = item.getDescription();
        this.mcGillMart = item.getMcGillMart();
    }

    public ItemDTO(String errorMessage) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public McGillMart getMcGillMart() {
        return mcGillMart;
    }

    public void setMcGillMart(McGillMart mcGillMart) {
        this.mcGillMart = mcGillMart;
    }

    public static List<ItemDTO> itemListToItemDTOList(List<Item> items) {
        return items.stream().map(ItemDTO::new).collect(Collectors.toList());
    }
}
