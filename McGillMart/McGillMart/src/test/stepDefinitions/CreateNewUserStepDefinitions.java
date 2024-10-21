package stepDefinitions;

import static org.junit.Assert.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import com.model.McGillMart;
import com.model.User;
import com.services.UserService;

import java.util.List;
import java.util.Map;

@SpringBootTest
@ContextConfiguration
public class CreateNewUserStepDefinitions {

    @Autowired
    private UserService userService;
    private Exception caughtException;
    private int initialCount = 0;

    @Given("the following user accounts exist in the system")
    public void the_following_user_accounts_exist_in_the_system(DataTable dataTable) {
        initialCount = userService.findAllUsers().size();
        List<Map<String, String>> userMaps = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> userMap : userMaps) {
            String name = user.setName(userMap.get("name"));
            String email = user.setEmail(userMap.get("email"));
            String phone = user.setPhone(userMap.get("phone"));
            String password = user.setPassword(userMap.get("password"));
            userService.createUser(name, email, phone, password);
        }
    }

    @When("a new user attempts to register with {string}, {string}, {string}, and {string}")
    public void a_new_user_attempts_to_register_with(String email, String name, String password, String phone) {
        try {
            userService.createUser(name, email, phone, password);
        } catch (Exception e) {
            caughtException = e;
        }

    }

    @Then("a new user account shall exist with {string}, {string}, {string}, and {string}")
    public void a_new_user_account_shall_exist_with(String email, String name, String password, String phone) {
        User newUser = userService.findUserByEmail(email);
        assertEquals(name, newUser.getName());
        assertEquals(email, newUser.getEmail());
        assertEquals(phone, newUser.getPhone());
        assertEquals(password, newUser.getPassword());
    }

    @Then("the error {string} shall be raised")
    public void the_error_shall_be_raised(String errorMessage) {
        assertNotNull(caughtException, "No exception was thrown");
        assertEquals(errorMessage, caughtException.getMessage());
    }

    @Then("the number of users in the system shall be {int}")
    public void the_number_of_users_in_the_system_shall_be(int users){
        int count = userService.findAllUsers().size() - initialCount;
        asserEquals(users, count);
    }
    @Then("the following users shall exist in the system")
    public void the_following_user_accounts_exist_in_the_system(DataTable dataTable) {
        List<Map<String, String>> userMaps = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> userMap : userMaps) {
            String name = user.setName(userMap.get("name"));
            String email = user.setEmail(userMap.get("email"));
            String phone = user.setPhone(userMap.get("phone"));
            String password = user.setPassword(userMap.get("password"));
            User user = userService.findByEmail(email);
            assertEquals(name, user.getName());
            assertEquals(email, user.getEmail());
            assertEquals(phone, user.getPhone());
            assertEquals(password, user.getPassword());
        }
    }

}