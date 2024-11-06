package com.mcgillmart.McGillMart.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.services.ItemService;

import io.cucumber.datatable.DataTable;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterItemsStepDefinitions {

    @Autowired
    private ItemService itemService;

    private List<Item> filteredItems = new ArrayList<>();

    @Given("the following items exist in the system \\(ID006)")
    public void the_following_items_exist_in_the_system(DataTable data) {
        List<Map<String, String>> dataTable = data.asMaps(String.class, String.class);
        for (Map<String, String> row : dataTable) {
            String name = row.get("name");
            double price = Double.parseDouble(row.get("price"));
            String description = row.get("description");
            String category = row.get("category");
            String url = "";
            itemService.createItem(name, price, description, category, url);
        }
    }

    @When("the user searches for items with the name containing {string} \\(ID006)")
    public void the_user_searches_for_items_with_the_name_containing(String searchTerm) {
        filteredItems = itemService.findItemsByNameContaining(searchTerm);
    }

    @When("the user attempts to only view the {string} items in the system \\(ID006)")
    public void the_user_attempts_to_only_view_the_items_in_the_system(String category) {
        filteredItems = itemService.filterItemsByCategory(category);
    }

    @Then("the following items shall be presented \\(ID006)")
    public void the_following_items_shall_be_presented(List<Map<String, String>> expectedItems) {
        List<Map<String, String>> actualItems = filteredItems.stream()
            .map(item -> Map.of(
                    "price", String.valueOf(item.getPrice()),
                    "description", item.getDescription(),
                    "category", String.valueOf(item.getCategory())
            ))
            .collect(Collectors.toList());

        // Check if both lists have the same size
        assertEquals(expectedItems.size(), actualItems.size());

        // Iterate through each item in both lists and compare them
        for (int i = 0; i < actualItems.size(); i++) {
            Map<String, String> expected = expectedItems.get(i);
            Map<String, String> actual = actualItems.get(i);

            assertEquals(expected.get("price"), actual.get("price"));
            assertEquals(expected.get("description"), actual.get("description"));
            assertEquals(expected.get("category"), actual.get("category"));
        }
    }
}