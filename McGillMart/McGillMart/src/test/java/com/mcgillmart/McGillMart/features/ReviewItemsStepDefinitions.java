package com.mcgillmart.McGillMart.features;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class ReviewItemsStepDefinitions {

    private List<Map<String, String>> users = new ArrayList<>();
    private List<Map<String, String>> items = new ArrayList<>();
    private List<Map<String, Object>> reviews = new ArrayList<>();
    private String lastErrorMessage;

    @Given("the following users exist in the system \\(ID007)")
    public void the_following_users_exist_in_the_system(DataTable userData) {
        users.clear();
        users.addAll(userData.asMaps(String.class, String.class));
    }

    @Given("the following items exist in the system \\(ID007)")
    public void the_following_items_exist_in_the_system(DataTable itemsData) {
        items.clear();
        items.addAll(itemsData.asMaps(String.class, String.class));
    }

    @When("the user with email {string} submits a review for {string} with a rating of {int} and a comment {string} \\(ID007)")
    public void the_user_submits_a_review(String email, String itemName, Integer rating, String comment) {
        Map<String, String> user = findUserByEmail(email);
        assertNotNull(user, "User should exist");

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

        // Create and store the review
        Map<String, Object> review = Map.of(
            "user", email,
            "item", itemName,
            "rating", rating,
            "comment", comment
        );
        reviews.add(review);
    }

    @Then("a new review for {string} with rating {int} and comment {string} shall be created \\(ID007)")
    public void a_new_review_for_item_shall_be_created(String itemName, Integer rating, String comment) {
        boolean reviewExists = reviews.stream()
            .anyMatch(review -> review.get("item").equals(itemName) && review.get("rating").equals(rating) && review.get("comment").equals(comment));
        assertTrue(reviewExists, "Review should exist");
    }

    @Then("the total number of reviews for {string} shall be {int} \\(ID007)")
    public void the_total_number_of_reviews_for_item(String itemName, Integer expectedReviewCount) {
        long reviewCount = reviews.stream().filter(review -> review.get("item").equals(itemName)).count();
        assertEquals(expectedReviewCount.intValue(), (int) reviewCount, "Review count should match");
    }

    @Then("an error message {string} shall be displayed \\(ID007)")
    public void an_error_message_shall_be_displayed(String expectedErrorMessage) {
        assertEquals(expectedErrorMessage, lastErrorMessage, "Error message should match");
    }

    // Helper methods
    private Map<String, String> findUserByEmail(String email) {
        return users.stream()
            .filter(user -> user.get("email").equals(email))
            .findFirst()
            .orElse(null);
    }

    private Map<String, String> findItemByName(String name) {
        return items.stream()
            .filter(item -> item.get("name").equals(name))
            .findFirst()
            .orElse(null);
    }
}
