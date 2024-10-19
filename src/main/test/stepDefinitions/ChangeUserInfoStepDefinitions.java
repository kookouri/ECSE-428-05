package main.test.stepDefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class ChangeUserInfoStepDefinitions {

    private Map<String, User> users = new HashMap<>();
    private User user;

    @Given("the following users exist in the system")
    public void the_following_users_exist_in_the_system(Map<String, User>  dataTable) {
        users.clear();
        users.addAll(dataTable);
    }

    @When("the user updates the name field to {string} and the phone field to {string} for user with id {string}")
    public void the_user_updates_name_and_phone_for_user(String Newname, String Newphone, String userId) {
        user = users.get(userId);
        if (user != null) {
            user.setName(Newname);
            user.setPhone(Newphone);
        }
    }

    @Then("the user profile should show the updated name as {string}")
    public void the_user_profile_should_show_updated_name(String expectedName) {
        assertEquals(expectedName, user.getName());
    }

    @Then("the phone field should display {string}")
    public void the_phone_field_should_display(String expectedPhone) {
        assertEquals(expectedPhone, user.getPhone());
    }

    @Then("the email field should remain {string}")
    public void the_email_field_should_remain(String expectedEmail) {
        assertEquals(expectedEmail, user.getEmail());
    }

}
