package com.mcgillmart.McGillMart.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mcgillmart.McGillMart.model.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {
    
    ShoppingCart findById(int id);
}