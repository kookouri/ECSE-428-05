package com.mcgillmart.McGillMart.features;

import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.services.ItemService;
import com.mcgillmart.McGillMart.services.ShoppingService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoveItemStepDefinitions {

    @Autowired
    ShoppingService shoppingService;

    @Autowired
    ItemService itemService;

    String error;

    @Given("the following items exist in the system \\(ID010)")
    public void the_following_items_exist_in_the_system_id010(DataTable data) {
        List<Map<String, String>> dataMap = data.asMaps(String.class, String.class);
        for (Map<String, String> map : dataMap) {
            String name = map.get("name");
            double price = Double.parseDouble(map.get("price"));
            String description = map.get("description");
            String category = map.get("category");
            String url = map.get("url");
            itemService.createItem(name, price, description, category, url);
        }
    }

    @When("the user attempts to remove the item with id {string} \\(ID010)")
    public void the_user_attempts_to_remove_the_item_with_id_id010(String id) {
        try {
            itemService.deleteItem(Integer.parseInt(id));
        } catch (RuntimeException e) {
            error = e.getMessage();
        }
    }

    @Then("the item with id {string} shall no longer be present in the system \\(ID010)")
    public void the_item_with_id_shall_no_longer_be_present_in_the_system_id010(String id) {
        try {
            itemService.findItemById(Integer.parseInt(id));
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Item not found", error);
    }

    @Then("the remaining items shall be \\(ID010)")
    public void the_remaining_items_shall_be_id010(DataTable data) {
        List<Map<String, String>> dataMap = data.asMaps(String.class, String.class);
        List<Item> items = itemService.findAllItems();
        assertEquals(dataMap.size(), items.size()-1);
        for (Map<String, String> map : dataMap) {
            String name = map.get("name");
            boolean found = items.stream().anyMatch(item -> item.getName().equals(name));
            assertTrue(found);
        }
    }

    @Then("an error message {string} shall be displayed \\(ID010)")
    public void an_error_message_shall_be_displayed_id010(String expectedError) {
        assertEquals(expectedError, error);
    }

    @Then("the following items shall remain in the system \\(ID010)")
    public void the_following_items_shall_remain_in_the_system_id010(DataTable data) {
        List<Map<String, String>> dataMap = data.asMaps(String.class, String.class);
        List<Item> items = itemService.findAllItems();
        assertEquals(dataMap.size(), items.size());
        for (Map<String, String> map : dataMap) {
            String name = map.get("name");
            boolean found = items.stream().anyMatch(item -> item.getName().equals(name));
            assertTrue(found);
        }
    }
}