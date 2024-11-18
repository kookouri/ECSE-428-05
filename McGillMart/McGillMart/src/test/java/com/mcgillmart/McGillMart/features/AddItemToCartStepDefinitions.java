package com.mcgillmart.McGillMart.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.services.ItemService;
import com.mcgillmart.McGillMart.services.LoginService;
import com.mcgillmart.McGillMart.services.ShoppingService;
import com.mcgillmart.McGillMart.services.UserService;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddItemToCartStepDefinitions {
	@Autowired
	ShoppingService shoppingService;
	@Autowired
	ItemService itemService;
	@Autowired
	UserService userService;
	@Autowired
	LoginService loginService;
	
	User loggedInUser;
	String error;
	
	@Given("the following user exists in the system \\(ID009)")
	public void the_following_user_exists_in_the_system_id009(DataTable data) {
		Map<String, String> dataMap = data.asMaps(String.class, String.class).get(0);
		String name = dataMap.get("name");
		String email = dataMap.get("email");
		String phone = dataMap.get("phone");
		String password = dataMap.get("password");
		userService.createUser(email, name, password, phone);
	}
	
	@Given("I am logged in with the email {string} \\(ID009)")
	public void i_am_logged_in_with_the_email_id009(String email) {
		User user = userService.findUserByEmail(email);
		loggedInUser = loginService.login(email, user.getPassword()) ? user : null;
	}
	
	@Given("the following items exist in the system \\(ID009)")
	public void the_following_items_exist_in_the_system_id009(DataTable data) {
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
	
	@Given("I have an empty cart \\(ID009)")
	public void i_have_an_empty_cart_id009() {
		if (shoppingService.getShoppingCart(loggedInUser.getId()).size() != 0) {
			for (Item item: loggedInUser.getShoppingCart()) {
				loggedInUser.removeShoppingCart(item);
			}
		}
	}
	
	@Given("the following items are in my cart \\(ID009)")
	public void the_following_items_are_in_my_cart_id009(DataTable data) {
		List<Map<String, String>> dataMap = data.asMaps(String.class, String.class);
		for (Map<String, String> map : dataMap) {
			String name = map.get("name");
			Item item = itemService.findItemsByName(name);
            shoppingService.addItemToCart(loggedInUser.getId(), item.getId());
		}
	}
	
	@When("I attempt to add an item with name {string} to my cart \\(ID009)")
	public void i_attempt_to_add_an_item_with_name_to_my_cart_id009(String name) {
		try {
			Item item = itemService.findItemsByName(name);
			shoppingService.addItemToCart(loggedInUser.getId(), item.getId());
		} catch (RuntimeException e) {
			error = e.getMessage();
		}
	}
	
	@Then("I should see the item with name {string} and description {string} in my cart \\(ID009)")
	public void i_should_see_the_item_with_name_and_description_in_my_cart_id009(String name, String description) {
		boolean hasEncountered = false;
		for (Item item: shoppingService.getShoppingCart(loggedInUser.getId())) {
			if (item.getName().equals(name) && item.getDescription().equals(description)) {
				hasEncountered = true;
			}
		}
		assertTrue(hasEncountered);
	}
	
	@Then("the number of items in my cart should be {string} \\(ID009)")
	public void the_number_of_items_in_my_cart_should_be_id009(String num) {
		int numItems = shoppingService.getShoppingCart(loggedInUser.getId()).size();
		assertNotNull(numItems);
		assertEquals(Integer.parseInt(num), numItems);
	}
	
	@Then("an error shall be raised \\(ID009)")
	public void an_error_shall_be_raised_id009() {
		assertNotNull(error);
	}
	
}
