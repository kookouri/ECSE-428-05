package com.mcgillmart.McGillMart.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class QueryItemsStepDefinitions {

    private List<Map<String, String>> items = new ArrayList<>();
    private List<Map<String, String>> filteredItems = new ArrayList<>();

    @Given("the following items exist in the system")
    public void the_following_items_exist_in_the_system(List<Map<String, String>> dataTable) {
        items.clear();
        items.addAll(dataTable);
    }

    @When("the user searches for items with the name containing {string}")
    public void the_user_searches_for_items_with_the_name_containing(String searchTerm) {
        filteredItems.clear();
        for (Map<String, String> item : items) {
            if (item.get("name").contains(searchTerm)) {
                filteredItems.add(item);
            }
        }
    }

    @When("the user attempts to only view the {string} items in the system")
    public void the_user_attempts_to_only_view_the_category_items_in_the_system(String category) {
        filteredItems.clear();
        for (Map<String, String> item : items) {
            if (item.get("category").equals(category)) {
                filteredItems.add(item);
            }
        }
    }

    @Then("the following items shall be presented")
    public void the_following_items_shall_be_presented(List<Map<String, String>> expectedItems) {
        assertEquals(expectedItems, filteredItems);
    }
}
