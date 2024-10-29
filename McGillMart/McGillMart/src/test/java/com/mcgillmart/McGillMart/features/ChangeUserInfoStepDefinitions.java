package com.mcgillmart.McGillMart.features;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import io.cucumber.datatable.DataTable;

import com.mcgillmart.McGillMart.services.UserService;
import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.model.User;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


public class ChangeUserInfoStepDefinitions {
    int userId;

    @Autowired
    UserService userService;

    @Given("the following users exist in the system \\(ID004)")
    public void the_following_users_exist_in_the_system_id004(DataTable data) {
        List<Map<String, String>> dataTable = data.asMaps(String.class, String.class);
        for (Map<String, String> userMap : dataTable) {
            String name = userMap.get("name");
            String email = userMap.get("email");
            String phone = userMap.get("phone");
            String password = userMap.get("password");
            userService.createUser(email, name, password, phone);
        }
    }

    @When("the user updates the name field to {string} and the phone field to {string} for user with email {string} \\(ID004)")
    public void the_user_updates_name_field_to_and_phone_field_to_for_user_with_email_id004(String Newname, String Newphone, String email) {
        User user = userService.findUserByEmail(email);
        userId = user.getId();
        userService.updateUser(userId, user.getEmail(), Newname, user.getPassword(), Newphone);
    }

    @When("the user updates the email field to {string} for user with email {string} \\(ID004)")
    public void the_user_updates_the_email_field_to_for_user_with_email_id004(String newEmail, String oldEmail){
        User user = userService.findUserByEmail(oldEmail);
        userId = user.getId();
        userService.updateUser(userId, newEmail, user.getName(), user.getPassword(), user.getPhoneNumber());
    }

    @When("the user changes the password field for user with email {string} to {string} \\(ID004)")
    public void the_user_changes_the_password_field_for_user_with_email_to_id004(String email, String newPass){
        User user = userService.findUserByEmail(email);
        userId = user.getId();
        userService.updateUser(userId, user.getEmail(), user.getName(), newPass, user.getPhoneNumber());
    }

    @Then("the name field should display {string} \\(ID004)")
    public void the_name_field_should_display_id004(String expectedName) {
        User user = userService.findUserById(userId);
        assertEquals(expectedName, user.getName());
    }

    @Then("the phone field should display {string} \\(ID004)")
    public void the_phone_field_should_display_id004(String expectedPhone) {
        User user = userService.findUserById(userId);
        assertEquals(expectedPhone, user.getPhoneNumber());
    }

    @Then("the email field should display {string} \\(ID004)")
    public void the_email_field_should_display_id004(String expectedEmail) {
        User user = userService.findUserById(userId);
        assertEquals(expectedEmail, user.getEmail());
    }

    @Then("the user should be able to log in with the new password {string} \\(ID004)")
    public void the_user_should_be_able_to_log_in_with_the_new_password_id004(String newPass){
        User user = userService.findUserById(userId);
        assertEquals(newPass, user.getPassword());
    }

    @Then("the old password {string} should no longer be valid \\(ID004)")
    public void the_old_password_should_no_longer_be_valid(String oldPass){
        User user = userService.findUserById(userId);
        assertNotEquals(oldPass, user.getPassword());
    }
}
