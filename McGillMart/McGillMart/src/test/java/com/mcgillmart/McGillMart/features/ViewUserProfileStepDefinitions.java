package com.mcgillmart.McGillMart.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.services.LoginService;
import com.mcgillmart.McGillMart.services.UserService;
import com.mcgillmart.McGillMart.services.ViewProfileService;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest
public class ViewUserProfileStepDefinitions {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ViewProfileService viewProfileService;

    // Keep track of login state and test data
    private Map<Integer, User> users = new HashMap<>();

    private String errorMessage;
    private User loggedInUser;
    private String loggedInEmail;
    private User viewedUser = null;

    @Given("the following user accounts exist in the system \\(ID003)")
    public void the_following_user_accounts_exist_in_the_system(DataTable dataTable) {
        // Add all users from the table to the users map
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

    @Given("the user with email {string} is logged in \\(ID003)")
    public void the_user_with_id_is_logged_in(String email) {
        try {
            loggedInUser = userService.findUserByEmail(email);
            loggedInEmail = loggedInUser.getEmail();
            loginService.login(loggedInEmail, loggedInUser.getPassword());
        } catch (Exception e) {
            errorMessage = e.getMessage();
            loggedInUser = null;
            loggedInEmail = null;
        }
    }

    @Given("the user is not logged in \\(ID003)")
    public void the_user_is_not_logged_in() {
        loggedInUser = null;
        loggedInEmail = null;
    }

    @When("the user attempts to view their profile \\(ID003)")
    public void the_user_attempts_to_view_their_profile() {
        try {
            viewedUser = viewProfileService.viewUserProfile(loggedInEmail);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("the name {string}, email {string}, and phone {string} shall be displayed \\(ID003)")
    public void the_name_email_and_phone_shall_be_displayed(String expectedName, String expectedEmail, String expectedPhone) {
        assertNotNull(viewedUser);
        assertEquals(expectedName, viewedUser.getName());
        assertEquals(expectedEmail, viewedUser.getEmail());
        assertEquals(expectedPhone, viewedUser.getPhoneNumber());
    }

    @Then("the error {string} shall be raised \\(ID003)")
    public void the_error_shall_be_raised(String expectedError) {
        assertNotNull(errorMessage);
        assertEquals(expectedError, errorMessage);
    }
}
