package com.mcgillmart.McGillMart.features;


import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.List;
import java.util.Map;

import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.model.Item.Category;

public class ViewItemsStepDefinitions {

    // The McGillMart object that holds the items
    private McGillMart mcGillMart;
    private List<Item> viewedItems;

    // This runs before any scenarios to set up the environment
    public ViewItemsStepDefinitions() {
        mcGillMart = new McGillMart();
    }

    // Given the following items exist in the system
    @Given("the following items exist in the system")
    public void the_following_items_exist_in_the_system(List<Map<String, String>> itemsData) {
        for (Map<String, String> itemData : itemsData) {
            String name = itemData.get("name");
            double price = Double.parseDouble(itemData.get("price"));
            String description = itemData.get("description");
            String categoryString = itemData.get("category");
            Category category = Category.valueOf(categoryString);

            // Create a new Item and add it to the McGillMart
            Item item = new Item(name, price, description, category, mcGillMart);
            mcGillMart.getItems().add(item);
        }
    }

    // When the user attempts to view all items in the system
    @When("the user attempts to view all items in the system")
    public void the_user_attempts_to_view_all_items_in_the_system() {
        // View all items from McGillMart
        viewedItems = mcGillMart.getItems();
    }

    // Then the following items shall be presented
    @Then("the following items shall be presented")
    public void the_following_items_shall_be_presented(List<Map<String, String>> expectedItemsData) {
        // Check that the size of the viewed items matches the expected size
        assertEquals(expectedItemsData.size(), viewedItems.size());

        // Check each item
        for (int i = 0; i < expectedItemsData.size(); i++) {
            Map<String, String> expectedItemData = expectedItemsData.get(i);
            Item actualItem = viewedItems.get(i);

            // Check item details match
            assertEquals(expectedItemData.get("name"), actualItem.getName());
            assertEquals(Double.parseDouble(expectedItemData.get("price")), actualItem.getPrice(), 0.01);
            assertEquals(expectedItemData.get("description"), actualItem.getDescription());
            assertEquals(expectedItemData.get("category"), actualItem.getCategory().name()); // Convert enum to string
        }
    }
}
