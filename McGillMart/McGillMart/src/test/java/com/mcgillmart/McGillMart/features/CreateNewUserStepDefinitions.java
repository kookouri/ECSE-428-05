package com.mcgillmart.McGillMart.features;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import io.cucumber.datatable.DataTable;

import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.services.UserService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
public class CreateNewUserStepDefinitions {

    @Autowired
    private UserService userService;

    private Exception caughtException;
    private int initialCount = 0;

    @Given("the following user accounts exist in the system \\(ID001)")
    public void the_following_user_accounts_exist_in_the_system_id001(DataTable dataTable) {
        initialCount = userService.findAllUsers().size();
        List<Map<String, String>> userMaps = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> userMap : userMaps) {
            String name = userMap.get("name");
            String email = userMap.get("email");
            String phone = userMap.get("phone");
            String password = userMap.get("password");
            userService.createUser(email, name, password, phone);
        }
    }

    @When("a new user attempts to register with {string}, {string}, {string}, and {string} \\(ID001)")
    public void a_new_user_attempts_to_register_with_id001(String email, String password, String name, String phone) {
        try {
            userService.createUser(email, name, password, phone);
        } catch (Exception e) {
            caughtException = e;
        }

    }

    @Then("a new user account shall exist with {string}, {string}, {string}, and {string} \\(ID001)")
    public void a_new_user_account_shall_exist_with_id001(String email, String password, String name, String phone) {
        User newUser = userService.findUserByEmail(email);
        assertEquals(name, newUser.getName());
        assertEquals(email, newUser.getEmail());
        assertEquals(phone, newUser.getPhoneNumber());
        assertEquals(password, newUser.getPassword());
    }

    @Then("the following {string} shall be raised \\(ID001)")
    public void the_following_shall_be_raised_id001(String errorMessage) {
        assertNotNull(caughtException);
        assertEquals(errorMessage, caughtException.getMessage());
    }

    @Then("the number of users in the system shall be {string} \\(ID001)")
    public void the_number_of_users_in_the_system_shall_be_id001(String users){
        int count = userService.findAllUsers().size() - initialCount;
        assertEquals(Integer.parseInt(users), count);
    }
    @Then("the following users shall exist in the system \\(ID001)")
    public void the_following_users_shall_exist_in_the_system_id001(DataTable dataTable) {
        List<Map<String, String>> userMaps = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> userMap : userMaps) {
            String name = userMap.get("name");
            String email = userMap.get("email");
            String phone = userMap.get("phone");
            String password = userMap.get("password");
            User user = userService.findUserByEmail(email);
            assertEquals(name, user.getName());
            assertEquals(email, user.getEmail());
            assertEquals(phone, user.getPhoneNumber());
            assertEquals(password, user.getPassword());
        }
    }

}