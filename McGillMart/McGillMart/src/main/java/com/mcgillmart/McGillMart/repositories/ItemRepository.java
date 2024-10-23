package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.model.Item;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends CrudRepository<Item, Integer> {

    Item findItemByNameContaining(String name);

    @Query("SELECT i FROM Item i WHERE lower(c.name) LIKE lower(concat('%', :keyword, '%')) OR lower(c.description) LIKE lower(concat('%', :keyword, '%'))")
    List<Item> findItemsByKeywordInNameOrDescription(@Param("keyword") String keyword);

    List<Item> findAll();

    List<Item> findByCategory(Item.Category category);

}
