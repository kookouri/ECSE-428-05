package com.mcgillmart.McGillMart.features;

import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.model.Item.Category;

public class ViewItemsStepDefinitions {

    private McGillMart mcGillMart;
    private List<Item> viewedItems;
    private List<Item> filteredItems;

    public ViewItemsStepDefinitions() {
        mcGillMart = new McGillMart();
    }

    @Given("the following items exist in the system")
    public void the_following_items_exist_in_the_system(List<Map<String, String>> itemsData) {
        mcGillMart.getItems().clear();
        for (Map<String, String> itemData : itemsData) {
            String name = itemData.get("name");
            double price = Double.parseDouble(itemData.get("price"));
            String description = itemData.get("description");
            Category category = Category.valueOf(itemData.get("category"));

            Item item = new Item(name, price, description, category, mcGillMart);
            mcGillMart.getItems().add(item);
        }
    }

    @When("the user attempts to view all items in the system")
    public void the_user_attempts_to_view_all_items_in_the_system() {
        viewedItems = new ArrayList<>(mcGillMart.getItems());
    }

    @When("the user searches for items with the name containing {string}")
    public void the_user_searches_for_items_with_the_name_containing(String searchTerm) {
        filteredItems = new ArrayList<>();
        for (Item item : mcGillMart.getItems()) {
            if (item.getName().contains(searchTerm)) {
                filteredItems.add(item);
            }
        }
    }

    @When("the user attempts to only view the {string} items in the system")
    public void the_user_attempts_to_only_view_the_category_items_in_the_system(String category) {
        Category categoryEnum = Category.valueOf(category);
        filteredItems = new ArrayList<>();
        for (Item item : mcGillMart.getItems()) {
            if (item.getCategory() == categoryEnum) {
                filteredItems.add(item);
            }
        }
    }

    @Then("the following items shall be presented")
    public void the_following_items_shall_be_presented(List<Map<String, String>> expectedItemsData) {
        List<Item> itemsToVerify = filteredItems != null ? filteredItems : viewedItems;
        
        assertEquals(expectedItemsData.size(), itemsToVerify.size());

        for (int i = 0; i < expectedItemsData.size(); i++) {
            Map<String, String> expectedItemData = expectedItemsData.get(i);
            Item actualItem = itemsToVerify.get(i);

            assertEquals(expectedItemData.get("name"), actualItem.getName());
            assertEquals(Double.parseDouble(expectedItemData.get("price")), actualItem.getPrice(), 0.01);
            assertEquals(expectedItemData.get("description"), actualItem.getDescription());
            assertEquals(expectedItemData.get("category"), actualItem.getCategory().name());
        }
    }
}
