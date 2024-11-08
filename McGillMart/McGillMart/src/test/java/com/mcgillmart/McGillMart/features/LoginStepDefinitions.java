package com.mcgillmart.McGillMart.features;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.services.LoginService;
import com.mcgillmart.McGillMart.services.UserService;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest
@ContextConfiguration
public class LoginStepDefinitions {
    
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    private String errorMessage = "";
    private User loggedInUser = null;

    @Given("the following users exist in the system \\(ID002)")
    public void the_following_user_accounts_exist_in_the_system(DataTable dataTable) {
        List<Map<String, String>> userMaps = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> userMap : userMaps) {
            String name = userMap.get("name");
            String email = userMap.get("email");
            String phone = userMap.get("phone");
            String password = userMap.get("password");
            userService.createUser(email, name, password, phone);
        }
    }

    @When("the user attempts to login with the email {string} and the password {string} \\(ID002)")
    public void the_user_attempts_to_login(String email, String password) {
        try {
            loginService.login(email, password);
            loggedInUser = userService.findUserByEmail(email);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("{string} should be displayed in the account section \\(ID002)")
    public void the_name_should_be_displayed(String name) {
        assertEquals(name, loggedInUser.getName());
    }

    @Then("an error message should be displayed saying {string} \\(ID002)")
    public void an_error_message_should_be_displayed(String error) {
        System.out.println("ACTUAL ERROR MESSAGE: " + errorMessage);
        assertEquals(error, errorMessage);
    }
}