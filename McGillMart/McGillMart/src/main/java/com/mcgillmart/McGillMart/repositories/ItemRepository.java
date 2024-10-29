package com.mcgillmart.McGillMart.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mcgillmart.McGillMart.model.Item;

public interface ItemRepository extends CrudRepository<Item, Integer> {
    // The CrudRepository interface provides CRUD operations on the Item entity.
    // It provides generic methods such as:
    // save(S entity): Saves a given entity.
    // findById(ID id): Retrieves an entity by its ID.
    // findAll(): Returns all entities.
    // deleteById(ID id): Deletes the entity with the given ID.
    // count(): Returns the number of entities.
    
    // Assuming that names can be duplicated, we return a list of items.
    @Query("SELECT i FROM Item i WHERE i.name = :name")
    Item findItemByName(@Param("name") String name);

    @Query("SELECT i FROM Item i WHERE i.category = :category")
    List<Item> findByCategory(Item.Category category);

    @Query("SELECT i FROM Item i WHERE i.price >= :minPrice AND i.price <= :maxPrice")
    List<Item> findByPriceBetween(double minPrice, double maxPrice);

    @Query("SELECT i FROM Item i WHERE i.description LIKE %:keyword%")
    List<Item> findByDescriptionContaining(String keyword);

    @Query("SELECT i FROM Item i WHERE i.description LIKE %:keyword%")
    List<Item> findByNameContaining(String keyword);
}
