package com.repositories;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import com.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserById(int id);

    User findUserByEmail(String email);

    List<User> findByPhoneNumber(String phoneNumber);

    List<User> findByName(String name);

    boolean existsByEmail(String email);

    List<User> findAll();

}
