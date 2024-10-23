package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.model.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {
    
    ShoppingCart findById(int id);
}