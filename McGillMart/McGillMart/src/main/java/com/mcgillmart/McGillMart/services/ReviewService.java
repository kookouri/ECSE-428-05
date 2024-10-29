package com.mcgillmart.McGillMart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import com.mcgillmart.McGillMart.model.Review;
import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.repositories.ReviewRepository;
import com.mcgillmart.McGillMart.repositories.ItemRepository;
import com.mcgillmart.McGillMart.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Service class in charge of managing reviews. It implements following use cases: </p>
 * <p>Create, update, delete reviews</p>
 * @author Maxx
 */
@Service("reviewService")
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a new review to an item.
     *
     * @param email the user's email
     * @param phone the user's phone
     * @param password the user's password
     * @param itemName the name of the item
     * @param rating the rating given by the user
     * @param comment the review comment
     * @return the created Review object
     */
    @Transactional
    public Review addReview(String email, String phone, String password, String itemName, int rating, String comment) {
        logger.info("Adding review for item: {}", itemName);

        // Validate user credentials
        User user = validateUser(email, phone, password);

        // Find the item
        Item item = itemRepository.findItemByName(itemName);
        if (item == null) {
            throw new IllegalArgumentException("Item not found: " + itemName);
        }

        // Validate rating and comment
        validateReviewDetails(rating, comment);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Create and save the review
        Review review = new Review(0, rating, comment, currentDate, user.getName(), item);
        item.addReview(review);  // Add review to the item
        itemRepository.save(item);  // Save the item with the new review

        Review savedReview = reviewRepository.save(review);
        logger.info("Review added for item: {}", itemName);
        return savedReview;
    }

    /**
     * Retrieves all reviews for a given item.
     *
     * @param itemName the name of the item
     * @return the list of reviews for the item
     */
    @Transactional(readOnly = true)
    public List<Review> getReviewsForItem(String itemName) {
        logger.info("Retrieving reviews for item: {}", itemName);
        Item item = itemRepository.findItemByName(itemName);
        if (item == null) {
            throw new IllegalArgumentException("Item not found: " + itemName);
        }
        return item.getReviews();
    }

    /**
     * Deletes a review by its ID.
     *
     * @param reviewId the ID of the review to delete
     */
    @Transactional
    public void deleteReview(Integer reviewId) {
        logger.info("Deleting review with ID: {}", reviewId);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with ID: " + reviewId));

        // Remove review from associated item
        Item item = review.getItem();
        if (item != null) {
            item.removeReview(review);  // Remove the review from the item
            itemRepository.save(item);  // Update the item
        }

        reviewRepository.delete(review);  // Delete the review itself
        logger.info("Review deleted with ID: {}", reviewId);
    }

    /**
     * Validates user credentials (email, phone, password).
     *
     * @param email the email of the user
     * @param phone the phone number of the user
     * @param password the password of the user
     * @return the validated User object
     */
    private User validateUser(String email, String phone, String password) {
        logger.info("Validating user credentials for email: {}", email);
        User user = userRepository.findUserByEmail(email);
        if (user == null || !user.getPhoneNumber().equals(phone) || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return user;
    }

    /**
     * Validates review details.
     *
     * @param rating the rating to validate
     * @param comment the comment to validate
     */
    private void validateReviewDetails(int rating, String comment) {
        logger.info("Validating review details: rating = {}, comment = {}", rating, comment);
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        if (comment == null || comment.isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty.");
        }
    }
}
