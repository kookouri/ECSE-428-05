package com.mcgillmart.McGillMart.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mcgillmart.McGillMart.model.McGillMart;

public interface McGillMartRepository extends CrudRepository<McGillMart, Integer> {
    McGillMart findMcGillMartById(int id);
    @SuppressWarnings("null")
    List<McGillMart> findAll();
}
