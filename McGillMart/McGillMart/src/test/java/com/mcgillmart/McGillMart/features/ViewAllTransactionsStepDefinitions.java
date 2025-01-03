package com.mcgillmart.McGillMart.features;

import com.mcgillmart.McGillMart.model.Transaction;
import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.repositories.TransactionRepository;
import com.mcgillmart.McGillMart.repositories.UserRepository;
import com.mcgillmart.McGillMart.services.LoginService;
import com.mcgillmart.McGillMart.services.ShoppingService;
import com.mcgillmart.McGillMart.services.UserService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@Transactional
public class ViewAllTransactionsStepDefinitions {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ShoppingService shoppingService;

    private Map<Integer, User> users = new HashMap<>();
    private User loggedInUser = null;
    private List<Transaction> transactions = new ArrayList<>();

    @Given("the following users exist in the system \\(ID012)")
    public void the_following_users_exist_in_the_system(DataTable dataTable) {
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
    
    @And("the following transactions exist in the system \\(ID012)")
    public void the_following_items_exist_in_the_system(DataTable dataTable) {
        dataTable.asMaps().forEach(row -> {
            int userId = Integer.parseInt(row.get("userId"));
            User user = users.get(userId);
            float amount = Float.parseFloat(row.get("amount"));
            LocalDate dateOfPurchase = LocalDate.parse(row.get("dateOfPurchase"));
            String description = row.get("description");
            
            Transaction transaction = new Transaction(amount, dateOfPurchase, description, user);
            user.addHistory(transaction);

            transactionRepository.save(transaction);
            userRepository.save(user);
            transactions.add(transaction);
        });
    }

    @Given("I am logged in with {string} \\(ID012)")
    public void i_am_logged_in_with(String email) {
        loggedInUser = userService.findUserByEmail(email);
        loginService.login(loggedInUser.getEmail(), loggedInUser.getPassword());
    }

    @When("I request to view my transaction history \\(ID012)")
    public void i_request_to_view_my_transaction_history() {
        transactions = shoppingService.getTransactions(loggedInUser.getId());
    }

    @Then("I should see {int} transactions in my history \\(ID012)")
    public void i_should_see_transactions_in_my_history(int transactionCount) {
        assertEquals(transactionCount, transactions.size());
    }

    @And("I should see the following transaction details: \\(ID012)")
    public void i_should_see_the_following_transaction_details(List<Map<String, String>> transactionDetails) {
        assertEquals(transactionDetails.size(), transactions.size());
        for (int i = 0; i < transactionDetails.size(); i++) {
            Map<String, String> expectedData = transactionDetails.get(i);
            Transaction actualData = transactions.get(i);

            assertEquals(expectedData.get("description"), actualData.getDescription());
            assertEquals(Double.parseDouble(expectedData.get("amount")), actualData.getAmount(), 0.001);
        }
    }

}
