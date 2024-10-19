package com.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.util.ServiceUtils;
import com.model.Item;
import com.model.McGillMart;
import com.repositories.ItemRepository;

@Service("itemService")
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public ItemService(List<Map<String, String>> items) {
    }

    @Transactional
    public List<Map<String, String>> filterItemsByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        List<Item> items = itemRepository.findItemsByKeywordInNameOrDescription(name);
        return items.stream()
                .map(item -> Map.of(
                        "name", item.getName(),
                        "price", String.valueOf(item.getPrice()),
                        "description", item.getDescription()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Map<String, String>> filterItemsByCategory(String category) {
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty.");
        }
        Item.Category targetCategory;
        try {
            targetCategory = Item.Category.valueOf(category);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
        List<Item> items = itemRepository.findByCategory(targetCategory);
        return items.stream()
                .map(item -> Map.of(
                        "name", item.getName(),
                        "price", String.valueOf(item.getPrice()),
                        "description", item.getDescription()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public Item createItem(String name, double price, String description, McGillMart mcGillMart) {
        validateItemDetails(name, price, description, mcGillMart);
        Item item = new Item(name, price, description, mcGillMart);
        return itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Integer id, String name, double price, String description, McGillMart mcGillMart) {
        // if (id == null) {
        //     throw new IllegalArgumentException("ID cannot be null.");
        // }
        validateItemDetails(name, price, description, mcGillMart);
        Item item = findItemById(id);
        item.setName(name);
        item.setPrice(price);
        item.setDescription(description);
        item.setMcGillMart(mcGillMart);
        return itemRepository.save(item);
    }

    @Transactional
    public void deleteItem(Integer id) {
        // if (id == null) {
        //     throw new IllegalArgumentException("ID cannot be null.");
        // }
        Item item = findItemById(id);
        item.delete();
        itemRepository.save(item);
        itemRepository.delete(item);
    }

    @Transactional
    public Item findItemById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) {
            throw new IllegalArgumentException("There is no item with ID " + id + ".");
        }
        return item;
    }

    @Transactional
    public List<Item> findAllItems() {
        return ServiceUtils.toList(itemRepository.findAll());
    }

    private void validateItemDetails(String name, double price, String description, McGillMart mcGillMart) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
        if (mcGillMart == null) {
            throw new IllegalArgumentException("McGillMart cannot be null.");
        }
    }
}