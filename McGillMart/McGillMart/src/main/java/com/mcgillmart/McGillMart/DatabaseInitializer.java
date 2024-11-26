package com.mcgillmart.McGillMart;

import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mcgillmart.McGillMart.services.ItemService;
import com.mcgillmart.McGillMart.services.McGillMartService;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    
    @Autowired
    private McGillMartService mcgillMartService;

    @Autowired
    private ItemService itemService;

    @Override
    public void run(String... args) throws Exception {
        initializeDatabase();
    }

    private void initializeDatabase() {
        // If the unique mcgillMart is not there, create it and initializes
        if (mcgillMartService.getAllMcGillMarts().isEmpty()) {
            System.err.println("McGillMart created");
            mcgillMartService.createMcGillMart();
            itemService.createItem("Test Item 1", 0.0, "test description", "Clothing", "https://test.lejames.ca/sites/default/files/catalogue/images/12965847_14485.jpg");
            itemService.createItem("Test Item 2", 0.0, "test description", "Clothing", "https://test.lejames.ca/sites/default/files/catalogue/images/12965847_14485.jpg");
            itemService.createItem("Test Item 3", 0.0, "test description", "Clothing", "https://test.lejames.ca/sites/default/files/catalogue/images/12965847_14485.jpg");
            itemService.createItem("Test Item 4", 0.0, "test description", "Clothing", "https://test.lejames.ca/sites/default/files/catalogue/images/12965847_14485.jpg");
        }
        else {
            System.err.println("McGillMart already created");
        }
    }
}