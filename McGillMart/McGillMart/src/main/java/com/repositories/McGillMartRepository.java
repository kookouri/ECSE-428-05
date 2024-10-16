package com.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.model.McGillMart;

public interface McGillMartRepository extends CrudRepository<McGillMart, Integer> {
    McGillMart findSportCenterById(int id);
    @SuppressWarnings("null")
    List<McGillMart> findAll();
}
