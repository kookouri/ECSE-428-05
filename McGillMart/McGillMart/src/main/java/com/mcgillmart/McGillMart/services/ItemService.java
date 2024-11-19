package com.mcgillmart.McGillMart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.mcgillmart.McGillMart.util.ServiceUtils;
import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.Item.Category;

import com.mcgillmart.McGillMart.repositories.ItemRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* <p>Service class in charge of managing items. It implements following use cases: </p>
* <p>Create, update, delete items</p>
* @author Steve
*/
@Service("itemService")
public class ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private McGillMartService mcGillMartService;

    /**
     * Filters items by name.
     *
     * @param name the name to filter items by 
     * @return the item matching the name
     */
    @Transactional(readOnly = true)
    public Item findItemByName(String name) {
        logger.info("Filtering items by name: {}", name);
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        Item item = itemRepository.findItemByName(name);

        logger.info("Filtered item by name: {}", name);
        return item;
    }


    /**
     * Filters items by containing a name.
     *
     * @param name the name to filter items containing
     * @return the item matching the name
     */
    @Transactional(readOnly = true)
    public List<Item> findItemsByNameContaining(String name) {
        logger.info("Filtering items by name: {}", name);
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        List<Item> items = itemRepository.findByNameContaining(name);

        logger.info("Filtered item by name: {}", name);
        return items;
    }

    /**
     * Filters items by category.
     *
     * @param category the category to filter items by
     * @return a list of items matching the category
     */
    @Transactional(readOnly = true)
    public List<Item> filterItemsByCategory(String category) {
        logger.info("Filtering items by category: {}", category);
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty.");
        }
        Item.Category targetCategory;
        try {
            targetCategory = Item.Category.valueOf(category);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid category: {}", category, e);
            throw new IllegalArgumentException("Invalid category: " + category);
        }
        List<Item> items = itemRepository.findByCategory(targetCategory);

        logger.info("Filtered items by category: {}", category);
        return items;   
    }

    /**
     * Creates a new item.
     *
     * @param name the name of the item
     * @param price the price of the item
     * @param description the description of the item
     * @param category the category of the item
     * @param mcGillMart the McGillMart associated with the item
     * @return the created item
     */
    @Transactional
    public Item createItem(String name, double price, String description, String category, String url) {
        logger.info("Creating item with name: {}, price: {}, description: {}", name, price, description);
        validateItemDetails(name, price, description,category);
        if (findItemByName(name) != null) {
            throw new IllegalArgumentException("Item with name " + name + " already exists.");
        }
        Item item = new Item(name, price, description, Item.Category.valueOf(category), url, mcGillMartService.getMcGillMart());
        
        return itemRepository.save(item);
    }

    /**
     * Updates an existing item.
     *
     * @param id the ID of the item to update
     * @param name the new name of the item
     * @param price the new price of the item
     * @param description the new description of the item
     * @param category the new category of the item
     * @param mcGillMart the new McGillMart associated with the item
     * @return the updated item
     */
    @Transactional
    public Item updateItem(Integer id, String name, double price, String description, String category, String url) {
        logger.info("Updating item with ID: {}", id);
        validateItemDetails(name, price, description, category);
        Item item = findItemById(id);
        if (findItemByName(name) != null && !item.getName().equals(name)) {
            throw new IllegalArgumentException("Item with name " + name + " already exists.");
        }
        item.setName(name);
        item.setPrice(price);
        item.setDescription(description);
        item.setCategory(Category.valueOf(category));
        item.setUrl(url);
        item.setMcGillMart(mcGillMartService.getMcGillMart());
        Item updatedItem = itemRepository.save(item);
        logger.info("Updated item with ID: {}", updatedItem.getId());
        return updatedItem;
    }

    /**
     * Deletes an item.
     *
     * @param id the ID of the item to delete
     */
    @Transactional
    public void deleteItem(Integer id) {
        logger.info("Deleting item with ID: {}", id);
        Item item = findItemById(id);
        item.delete();
        itemRepository.save(item);
        itemRepository.delete(item);
        logger.info("Deleted item with ID: {}", id);
    }

    /**
     * Finds an item by ID.
     *
     * @param id the ID of the item to find
     * @return the found item
     */
    @Transactional(readOnly = true)
    public Item findItemById(Integer id) {
        logger.info("Finding item with ID: {}", id);
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) {
            throw new IllegalArgumentException("There is no item with ID " + id + ".");
        }
        logger.info("Found item with ID: {}", id);
        return item;
    }

    /**
     * Finds all items.
     *
     * @return a list of all items
     */
    @Transactional(readOnly = true)
    public List<Item> findAllItems() {
        logger.info("Finding all items");
        List<Item> items = ServiceUtils.toList(itemRepository.findAll());
        logger.info("Found {} items", items.size());
        return items;
    }

    /**
     * Validates item details.
     *
     * @param name the name of the item
     * @param price the price of the item
     * @param description the description of the item
     * @param category the category of the item
     * @param mcGillMart the McGillMart associated with the item
     */
    private void validateItemDetails(String name, double price, String category, String description) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty.");
        }
    }
}