package com.mcgillmart.McGillMart.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mcgillmart.McGillMart.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    Transaction findTransactionById(int id);
}
