package com.mcgillmart.McGillMart.features;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mcgillmart.McGillMart.services.ItemService;
import com.mcgillmart.McGillMart.services.LoginService;
import com.mcgillmart.McGillMart.services.ShoppingService;
import com.mcgillmart.McGillMart.services.UserService;
import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.User;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
public class ViewCartStepDefinitions {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private ItemService itemService;

    private Map<Integer, User> users = new HashMap<>();
    private User loggedInUser = null;
    private List<Item> shoppingCartItems = new ArrayList<>();

    @Given("the following users exist in the system \\(ID008)")
    public void the_following_user_accounts_exist_in_the_system(DataTable dataTable) {
        dataTable.asMaps().forEach(row -> {
            int id = Integer.parseInt(row.get("id"));
            String name = row.get("name");
            String email = row.get("email");
            String phone = row.get("phone");
            String password = row.get("password");

            User user = userService.createUser(email, name, password, phone);
            users.put(id, user);
        });
    }

    @And("the following items exist in Alice's cart \\(ID008)")
    public void the_following_items_exist_in_Alice_cart(DataTable dataTable) {
        User alice = users.get(1);
        
        dataTable.asMaps().forEach(row -> {
            int id = Integer.parseInt(row.get("id"));
            String name = row.get("name");
            String price = row.get("price");
            String description = row.get("description");
            String category = row.get("category");
            String url = row.get("url");
            Item item = itemService.createItem(name, Double.parseDouble(price), description, category, url);
            shoppingService.addItemToCart(alice.getId(), item.getId());
        });
    }

    @And("the following items exist in Bob's cart \\(ID008)")
    public void the_following_items_exist_in_Bob_cart(DataTable dataTable) {
        User bob = users.get(2);

        dataTable.asMaps().forEach(row -> {
            int id = Integer.parseInt(row.get("id"));
            String name = row.get("name");
            String price = row.get("price");
            String description = row.get("description");
            String category = row.get("category");
            String url = row.get("url");
            Item item = itemService.createItem(name, Double.parseDouble(price), description, category, url);
            shoppingService.addItemToCart(bob.getId(), item.getId());
        });
    }

    @Given("I am logged in as {string} \\(ID008)")
    public void i_am_logged_in_as(String email) {
        loggedInUser = userService.findUserByEmail(email);
        loginService.login(loggedInUser.getEmail(), loggedInUser.getPassword());
    }

    @When("I attempt to view my cart \\(ID008)")
    public void i_attempt_to_view_my_cart() {
        // Use the shopping service instead of directly accessing the user's cart
        shoppingCartItems = shoppingService.getShoppingCart(loggedInUser.getId());
    }

    @Then("I should see {int} items in my cart \\(ID008)")
    public void i_should_see_x_items_in_my_cart(int numItems) {
        assertEquals(numItems, shoppingCartItems.size());
    }

    @And("I should see the following items: \\(ID008)")
    public void i_should_see_the_following_items(List<Map<String, String>> expectedItemsData) {
        assertEquals(expectedItemsData.size(), shoppingCartItems.size());

        for (int i = 0; i < expectedItemsData.size(); i++) {
            Map<String, String> expectedItemData = expectedItemsData.get(i);
            Item actualItem = shoppingCartItems.get(i);

            assertEquals(expectedItemData.get("name"), actualItem.getName());
            assertEquals(Double.parseDouble(expectedItemData.get("price")), actualItem.getPrice(), 0);
            assertEquals(expectedItemData.get("description"), actualItem.getDescription());
            assertEquals(expectedItemData.get("category"), actualItem.getCategory().name());
        }
    }

    @Then("the total cart value should be {string} \\(ID008)")
    public void total_cart_value(String expectedTotal) {
        double actualTotal = shoppingService.getShoppingCartTotal(loggedInUser.getId());
        assertEquals(Double.parseDouble(expectedTotal), actualTotal, 0);
    }

    @Given("I have removed all items from my cart \\(ID008)")
    public void remove_all_items_from_cart() {
        shoppingService.emptyCart(loggedInUser.getId());
    }


}