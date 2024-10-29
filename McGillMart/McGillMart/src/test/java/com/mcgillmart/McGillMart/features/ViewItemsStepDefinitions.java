package com.mcgillmart.McGillMart.features;


import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.services.ItemService;

public class ViewItemsStepDefinitions {

    private List<Item> viewedItems;

    @Autowired
    private ItemService itemService;

    // Given the following items exist in the system
    @Given("the following items exist in the system \\(ID005)")
    public void the_following_items_exist_in_the_system_id005(List<Map<String, String>> itemsData) {
        for (Map<String, String> itemData : itemsData) {
            String name = itemData.get("name");
            double price = Double.parseDouble(itemData.get("price"));
            String description = itemData.get("description");
            String category = itemData.get("category");

            // Create a new Item and add it to the McGillMart
            itemService.createItem(name, price, description, category);
        }
    }

    // When the user attempts to view all items in the system
    @When("the user attempts to view all items in the system \\(ID005)")
    public void the_user_attempts_to_view_all_items_in_the_system_id005() {
        viewedItems = itemService.findAllItems();
    }

    // Then the following items shall be presented
    @Then("the following items shall be presented \\(ID005)")
    public void the_following_items_shall_be_presented_id005(List<Map<String, String>> expectedItemsData) {
        // Check that the size of the viewed items matches the expected size
        assertEquals(expectedItemsData.size(), viewedItems.size());

        // Check each item
        for (int i = 0; i < expectedItemsData.size(); i++) {
            Map<String, String> expectedItemData = expectedItemsData.get(i);
            Item actualItem = viewedItems.get(i);

            // Check item details match
            assertEquals(expectedItemData.get("name"), actualItem.getName());
            assertEquals(Double.parseDouble(expectedItemData.get("price")), actualItem.getPrice());
            assertEquals(expectedItemData.get("description"), actualItem.getDescription());
            assertEquals(expectedItemData.get("category"), actualItem.getCategory().name()); // Convert enum to string
        }
    }
}
