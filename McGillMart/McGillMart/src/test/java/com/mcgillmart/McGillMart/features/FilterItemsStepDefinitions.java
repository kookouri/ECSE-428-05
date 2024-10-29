package com.mcgillmart.McGillMart.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class FilterItemsStepDefinitions {

    private List<Map<String, String>> items = new ArrayList<>();
    private List<Map<String, String>> filteredItems = new ArrayList<>();

    @Given("the following items exist in the system \\(ID006)")
    public void the_following_items_exist_in_the_system_id006(List<Map<String, String>> dataTable) {
        items.clear();
        items.addAll(dataTable);
    }

    @When("the user searches for items with the name containing {string} \\(ID006)")
    public void the_user_searches_for_items_with_the_name_containing_id006(String searchTerm) {
        filteredItems.clear();
        for (Map<String, String> item : items) {
            if (item.get("name").contains(searchTerm)) {
                filteredItems.add(item);
            }
        }
    }

    @When("the user attempts to only view the {string} items in the system \\(ID006)")
    public void the_user_attempts_to_only_view_the_category_items_in_the_system_id006(String category) {
        filteredItems.clear();
        for (Map<String, String> item : items) {
            if (item.get("category").equals(category)) {
                filteredItems.add(item);
            }
        }
    }

    @Then("the following items shall be presented \\(ID006)")
    public void the_following_items_shall_be_presented_id006(List<Map<String, String>> expectedItems) {
        assertEquals(expectedItems, filteredItems);
    }
}
