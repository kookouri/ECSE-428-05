// package com.mcgillmart.McGillMart.features;

// import io.cucumber.datatable.DataTable;
// import io.cucumber.java.en.Given;
// import io.cucumber.java.en.When;
// import io.cucumber.java.en.Then;
// import static org.junit.jupiter.api.Assertions.*;
// import java.util.List;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;

// import com.mcgillmart.McGillMart.model.Item;
// import com.mcgillmart.McGillMart.model.User;
// import com.mcgillmart.McGillMart.services.ItemService;

// public class FilterItemsStepDefinitions {

//     @Autowired
//     private ItemService itemService;

//     private List<Map<String, String>> filteredItems = new ArrayList<>();
//     private Map<Long, Item> items = new HashMap<>();

//     @Given("the following items exist in the system \\(ID006)")
//     public void the_following_items_exist_in_the_system_id006(DataTable dataTable) {
//         dataTable.asMaps().forEach(row -> {
//             Long id = Long.parseLong(row.get("id"));
//             String name = row.get("name");
//             Double price = Double.parseDouble(row.get("price"));
//             String description = row.get("description");
//             String category = row.get("category");

//             Item item = itemService.createItem(name, price, description, category);
//             items.put(id, item);
//         });
//     }

//     @When("the user searches for items with the name containing {string} \\(ID006)")
//     public void the_user_searches_for_items_with_the_name_containing_id006(String searchTerm) {
        
//     }

//     @When("the user attempts to only view the {string} items in the system \\(ID006)")
//     public void the_user_attempts_to_only_view_the_category_items_in_the_system_id006(String category) {
//         filteredItems.clear();
//         for (Map<String, String> item : items) {
//             if (item.get("category").equals(category)) {
//                 filteredItems.add(item);
//             }
//         }
//     }

//     @Then("the following items shall be presented \\(ID006)")
//     public void the_following_items_shall_be_presented_id006(List<Map<String, String>> expectedItems) {
//         assertEquals(expectedItems, filteredItems);
//     }
// }
