package com.mcgillmart.McGillMart.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mcgillmart.McGillMart.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserById(int id);

    User findUserByEmail(String email);

    List<User> findByPhoneNumber(String phoneNumber);

    List<User> findByName(String name);

    boolean existsByEmail(String email);

    @SuppressWarnings("null")
    List<User> findAll();

}
