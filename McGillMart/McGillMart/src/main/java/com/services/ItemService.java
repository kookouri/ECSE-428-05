package com.services;

import com.model.Item;
import com.model.McGillMart;

import java.util.List;
import java.util.Map;

public class ItemService {
    public ItemService(List<Map<String, String>> items) {
    }

    public List<Map<String, String>> filterItemsByName(String hoodie) {
        return null;
    }

    public List<Map<String, String>> filterItemsByCategory(String clothing) {
    }

    public Item createItem(String name, double price, String description, McGillMart mcGillMart) {
    }

    public Item updateItem(Integer id, String name, double price, String description, McGillMart mcGillMart) {
    }

    public void deleteItem(Integer id) {
    }

    public Item findItemById(Integer id) {
    }

    public List<Item> findAllItems() {
        return null;
    }
}
