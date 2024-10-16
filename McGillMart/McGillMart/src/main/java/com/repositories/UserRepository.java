package com.repositories;

import org.springframework.data.repository.CrudRepository;

import com.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserById(int id);
    User findUserByEmail(String email);
}
