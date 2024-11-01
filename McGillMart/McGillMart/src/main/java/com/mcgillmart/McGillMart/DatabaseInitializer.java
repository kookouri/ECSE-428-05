package com.mcgillmart.McGillMart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mcgillmart.McGillMart.services.McGillMartService;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    
    @Autowired
    private McGillMartService mcgillMartService;

    @Override
    public void run(String... args) throws Exception {
        initializeDatabase();
    }

    private void initializeDatabase() {
        // If the unique mcgillMart is not there, create it and initializes
        if (mcgillMartService.getAllMcGillMarts().isEmpty()) {
            System.err.println("McGillMart created");
            mcgillMartService.createMcGillMart();
        }
        else {
            System.err.println("McGillMart already created");
        }
    }
}