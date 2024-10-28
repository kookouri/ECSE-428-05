package com.mcgillmart.McGillMart.features;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.mcgillmart.McGillMart.model.User;

public class ChangeUserInfoStepDefinitions {

    private List<Map<String, User>> users = new ArrayList<>();
    private User user;

    @Given("the following users exist in the system")
    public void the_following_users_exist_in_the_system(List<Map<String, User>> dataTable) {
        users.clear();
        users.addAll(dataTable);
    }

    @When("the user updates the name field to {string} and the phone field to {string} for user with id {int}")
    public void the_user_updates_name_and_phone_for_user(String Newname, String Newphone) {
        if (user != null) {
            user.setName(Newname);
            user.setPhoneNumber(Newphone);
        }
    }

    @Then("the user profile should show the updated name as {string}")
    public void the_user_profile_should_show_updated_name(String expectedName) {
        assertEquals(expectedName, user.getName());
    }

    @Then("the phone field should display {string}")
    public void the_phone_field_should_display(String expectedPhone) {
        assertEquals(expectedPhone, user.getPhoneNumber());
    }

    @Then("the email field should remain {string}")
    public void the_email_field_should_remain(String expectedEmail) {
        assertEquals(expectedEmail, user.getEmail());
    }

}
