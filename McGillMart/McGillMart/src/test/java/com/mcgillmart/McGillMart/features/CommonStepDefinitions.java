package com.mcgillmart.McGillMart.features;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.repositories.ItemRepository;
import com.mcgillmart.McGillMart.repositories.McGillMartRepository;
import com.mcgillmart.McGillMart.repositories.ReviewRepository;
import com.mcgillmart.McGillMart.repositories.TransactionRepository;
import com.mcgillmart.McGillMart.repositories.UserRepository;

import io.cucumber.java.Before;

public class CommonStepDefinitions {

    @Autowired
    McGillMartRepository mcgillmartRepo;

    @Autowired
    ItemRepository itemRepo;

    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    TransactionRepository transactionRepo;
    
    @Autowired
    UserRepository userRepo;

    @Before
    public void setup() {
        clearDatabase();
        mcgillmartRepo.save(new McGillMart());
    }

    @AfterEach
    public void tearDown() {
        clearDatabase();
    }

    @AfterAll
    public void tearDownEnd() {
        clearDatabase();
    }

    private void clearDatabase(){
        reviewRepo.deleteAll();
        mcgillmartRepo.deleteAll();
        itemRepo.deleteAll();
        userRepo.deleteAll();
    }
}
