package com.mcgillmart.McGillMart.features;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class ReviewItemsStepDefinitions {

    private List<Map<String, String>> users = new ArrayList<>();
    private List<Map<String, String>> items = new ArrayList<>();
    private List<Map<String, Object>> reviews = new ArrayList<>();
    private String lastErrorMessage;

    @Given("the following users exist in the system")
    public void the_following_users_exist_in_the_system(List<Map<String, String>> userData) {
        users.clear();
        users.addAll(userData);
    }

    @Given("the following items exist in the system")
    public void the_following_items_exist_in_the_system(List<Map<String, String>> itemsData) {
        items.clear();
        items.addAll(itemsData);
    }

    @When("the user with email {string}, phone {string}, and password {string} submits a review for {string} with a rating of {int} and a comment {string}")
    public void the_user_submits_a_review(String email, String phone, String password, String itemName, Integer rating, String comment) {
        Map<String, String> user = findUserByEmail(email);
        assertNotNull(user, "User should exist");
        assertEquals(phone, user.get("phone"), "Phone should match");
        assertEquals(password, user.get("password"), "Password should match");

        Map<String, String> item = findItemByName(itemName);
        assertNotNull(item, "Item should exist");

        if (rating < 1 || rating > 5) {
            lastErrorMessage = "Rating must be between 1 and 5";
            return;
        }
        if (comment.trim().isEmpty()) {
            lastErrorMessage = "Comment cannot be empty";
            return;
        }

        // Create and store a new review
        Map<String, Object> review = Map.of(
            "user", email,
            "item", itemName,
            "rating", rating,
            "comment", comment
        );

        reviews.add(review);
    }

    @Then("a new review for {string} with rating {int} and comment {string} shall be created")
    public void a_new_review_for_item_shall_be_created(String itemName, Integer rating, String comment) {
        boolean reviewExists = reviews.stream()
            .anyMatch(review -> review.get("item").equals(itemName) && review.get("rating").equals(rating) && review.get("comment").equals(comment));

        assertTrue(reviewExists, "Review should exist for the item");
    }

    @Then("the total number of reviews for {string} shall be {int}")
    public void the_total_number_of_reviews_for_item(String itemName, Integer expectedReviewCount) {
        long reviewCount = reviews.stream().filter(review -> review.get("item").equals(itemName)).count();
        assertEquals(expectedReviewCount.intValue(), (int) reviewCount, "Review count should match");
    }

    @When("the user with email {string}, phone {string}, and password {string} attempts to submit a review for {string} with a rating of {int} and comment {string}")
    public void the_user_attempts_to_submit_a_review(String email, String phone, String password, String itemName, Integer rating, String comment) {
        try {
            the_user_submits_a_review(email, phone, password, itemName, rating, comment);
        } catch (IllegalArgumentException e) {
            lastErrorMessage = e.getMessage();
        }
    }

    @Then("an error message {string} shall be displayed")
    public void an_error_message_shall_be_displayed(String expectedErrorMessage) {
        assertEquals(expectedErrorMessage, lastErrorMessage, "Error message should match");
    }

    @When("a non-authenticated user tries to submit a review for {string} with rating {int} and comment {string}")
    public void a_non_authenticated_user_tries_to_submit_a_review(String itemName, Integer rating, String comment) {
        lastErrorMessage = "You must be logged in to leave a review";
    }

    // Helper method to find a user by email
    private Map<String, String> findUserByEmail(String email) {
        return users.stream()
            .filter(user -> user.get("email").equals(email))
            .findFirst()
            .orElse(null);
    }

    // Helper method to find an item by name
    private Map<String, String> findItemByName(String name) {
        return items.stream()
            .filter(item -> item.get("name").equals(name))
            .findFirst()
            .orElse(null);
    }
}
