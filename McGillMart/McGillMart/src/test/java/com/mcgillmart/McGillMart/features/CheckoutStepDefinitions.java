package com.mcgillmart.McGillMart.features;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.services.ItemService;
import com.mcgillmart.McGillMart.services.McGillMartService;
import com.mcgillmart.McGillMart.services.ShoppingService;
import com.mcgillmart.McGillMart.services.UserService;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest
public class CheckoutStepDefinitions {

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private UserService userService;

    @Autowired
    private McGillMartService mcGillMartService;

    @Autowired
    private ItemService itemService;

    private String checkoutMessage;
    private Exception checkoutException;

    private Integer loggedInUserId;
    private McGillMart mcGillMart;

    @Before
    public void setUp() {
        // Initialize mcGillMart before any scenario runs
        mcGillMart = mcGillMartService.getMcGillMart();
        assertNotNull(mcGillMart);
    }

    @Given("the following user exists in the system \\(ID011)")
    public void the_following_user_exists_in_the_system(DataTable dataTable) {
        List<Map<String, String>> users = dataTable.asMaps();
        users.forEach(row -> {
            String email = row.get("email");
            String name = row.get("name");
            String password = row.get("password");
            String phone = row.get("phone");

            User user = userService.createUser(email, name, password, phone);
            assertNotNull(user);
        });
    }

    @And("I am logged in with the email {string} \\(ID011)")
    public void i_am_logged_in_with_the_email(String email) {
        User user = userService.findUserByEmail(email);
        assertNotNull(user);
        loggedInUserId = user.getId();
    }

    @And("the following items exist in the system \\(ID011)")
    public void the_following_items_exist_in_the_system(DataTable dataTable) {
        List<Map<String, String>> items = dataTable.asMaps();
        items.forEach(row -> {
            String itemName = row.get("name");
            double price = Double.parseDouble(row.get("price"));
            String description = row.get("description");
            String categoryStr = row.get("category").replace(" ", "_");
            Item.Category category;
            try {
                category = Item.Category.valueOf(categoryStr);
            } catch (IllegalArgumentException e) {
                // If category not found, default to OTHER
                category = Item.Category.Other;
            }
            String url = row.get("url");

            Item item = itemService.createItem(itemName, price, description, categoryStr, url);
            assertNotNull(item);
        });
    }

    @Given("the following items are in the user's shopping card \\(ID011)")
    public void the_following_items_are_in_the_user_s_shopping_cart(DataTable dataTable) {
        List<Map<String, String>> items = dataTable.asMaps();
        items.forEach(row -> {
            String itemName = row.get("name");
            
            Item item = itemService.findItemByName(itemName);

            shoppingService.addItemToCart(loggedInUserId, item.getId());
        });
    }

    @And("the number of items in my cart is {string} \\(ID011)")
    public void the_number_of_items_in_my_cart_is_id011(String expectedItemCount) {
        List<Item> cartItems = shoppingService.getShoppingCart(loggedInUserId);
        assertEquals(Integer.parseInt(expectedItemCount), cartItems.size());
    }

    @When("I attempt to check-out the shopping cart \\(ID011)")
    public void i_attempt_to_check_out_the_shopping_cart() {
        try {
            checkoutMessage = shoppingService.checkoutShoppingCart(loggedInUserId);
        } catch (Exception e) {
            checkoutException = e;
        }
    }

    @Then("the message {string} will be raised. \\(ID011)")
    public void the_message_will_be_raised(String expectedMessage) {
        assertNull(checkoutException);
        assertNotNull(checkoutMessage);
        assertEquals(expectedMessage, checkoutMessage);
    }

    @Then("the error {string} shall be raised \\(ID011)")
    public void the_error_shall_be_raised(String expectedError) {
        assertNotNull(checkoutException);
        assertEquals(expectedError, checkoutException.getMessage());
    }

    @And("the number of items in my cart should be {string} \\(ID011)")
    public void the_number_of_items_in_my_cart_should_be(String expectedItemCount) {
        List<Item> cartItems = shoppingService.getShoppingCart(loggedInUserId);
        assertEquals(Integer.parseInt(expectedItemCount), cartItems.size());
    }
}
