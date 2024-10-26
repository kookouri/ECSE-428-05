package com.mcgillmart.McGillMart.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mcgillmart.McGillMart.model.Item;

public interface ItemRepository extends CrudRepository<Item, Integer> {

    Item findItemByNameContaining(String name);
    
    List<Item> findByNameContainingOrDescriptionContaining(String name, String description);

    List<Item> findAll();

    List<Item> findByCategory(Item.Category category);

}
