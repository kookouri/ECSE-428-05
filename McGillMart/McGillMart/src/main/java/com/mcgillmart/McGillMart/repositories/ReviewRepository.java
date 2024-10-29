package com.mcgillmart.McGillMart.repositories;

import org.springframework.data.repository.CrudRepository;
import com.mcgillmart.McGillMart.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
