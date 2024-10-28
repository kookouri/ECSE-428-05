package com.mcgillmart.McGillMart.features;

import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.services.UserService;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
public class ViewUserProfileStepDefinitions {

    @Autowired
    private UserService userService;

    private User currentUser;
    private User viewedProfile;
    private Exception caughtException;

    @Given("the following user accounts exist in the system")
    public void the_following_user_accounts_exist_in_the_system(DataTable dataTable) {
        List<Map<String, String>> userMaps = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> userMap : userMaps) {
            User user = new User(
                    userMap.get("email"),
                    userMap.get("name"),
                    userMap.get("password"),
                    userMap.get("phoneNumber"), 
                    null
            );
            userService.createUser(
                    user.getEmail(),
                    user.getName(),
                    user.getPassword(),
                    user.getPhoneNumber()
            );
        }
    }
//TODO: implement
//    @Given("the user with id {string} is logged in")
//    public void the_user_with_id_is_logged_in(String id) {
//        currentUser = userService.findUserById(Integer.parseInt(id));
//        assertNotNull(currentUser, "User not found");
//        userService.loginUser(currentUser.getEmail(), currentUser.getPassword());
//    }
//TODO: implement
//    @Given("the user is not logged in")
//    public void the_user_is_not_logged_in() {
//        currentUser = null;
//        userService.logoutUser();
//    }

    @When("the user attempts to view their profile")
    public void the_user_attempts_to_view_their_profile() {
        try {
            viewedProfile = userService.findUserById(currentUser.getId());
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @Then("the name {string}, email {string}, and phone {string} shall be displayed")
    public void the_name_email_and_phone_shall_be_displayed(String name, String email, String phoneNumber) {
        assertNotNull(viewedProfile, "Profile was not retrieved");
        assertEquals(name, viewedProfile.getName());
        assertEquals(email, viewedProfile.getEmail());
        assertEquals(phoneNumber, viewedProfile.getPhoneNumber());
    }

    @Then("the error {string} shall be raised")
    public void the_error_shall_be_raised(String errorMessage) {
        assertNotNull(caughtException, "No exception was thrown");
        assertEquals(errorMessage, caughtException.getMessage());
    }
}