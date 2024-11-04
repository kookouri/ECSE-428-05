package com.mcgillmart.McGillMart.dto;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.mcgillmart.McGillMart.model.Item;

public class ShoppingCartDTO {
    List<ItemResponseDTO> shoppingCart = new ArrayList<>();
    String errorMessage;

    public ShoppingCartDTO() {

    }

    public ShoppingCartDTO(List<Item> shoppingCart) {
        this.shoppingCart = transactionListToItemResponseDTOList(shoppingCart);
    }

    public ShoppingCartDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static List<ItemResponseDTO> transactionListToItemResponseDTOList(List<Item> shoppingCart) {
        return shoppingCart.stream().map(ItemResponseDTO::new).collect(Collectors.toList());
    }

    public void setShoppingCart(List<ItemResponseDTO> shoppingCart) {
        for (ItemResponseDTO transaction : shoppingCart) {
            this.shoppingCart.add(transaction);
        }
    }

    public List<ItemResponseDTO> getShoppingCart() {return shoppingCart;}
}
