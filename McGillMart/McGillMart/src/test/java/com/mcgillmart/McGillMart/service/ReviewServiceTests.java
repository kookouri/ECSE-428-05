package com.mcgillmart.McGillMart.service;

import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.Review;
import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.repositories.ItemRepository;
import com.mcgillmart.McGillMart.repositories.ReviewRepository;
import com.mcgillmart.McGillMart.repositories.UserRepository;
import com.mcgillmart.McGillMart.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Item testItem;
    private User testUser;
    private Review testReview;
    private List<Review> reviewList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize the item with an empty reviews list
        testItem = new Item();
        testItem.setName("Test Item");
        testItem.setPrice(100.00);
        testItem.setDescription("Test Description");
        testItem.setCategory(Item.Category.Clothing);

        // Initialize a test user
        testUser = new User();
        testUser.setEmail("test@test.com");
        testUser.setPhoneNumber("1234567890");
        testUser.setPassword("password");

        // Initialize a test review
        testReview = new Review();
        testReview.setRating(5);
        testReview.setComment("Great product!");
        testReview.setDatePosted(LocalDate.now());
        testReview.setUsername(testUser.getEmail());
        testReview.setItem(testItem); // Associate the review with the item

        // Mock review list for the item
        reviewList = new ArrayList<>();
        reviewList.add(testReview);
    }

    @AfterEach
    public void clearDatabase() {
        // Clear the database after each test
        reviewRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testAddReview() {
        when(userRepository.findUserByEmail(testUser.getEmail())).thenReturn(testUser);
        when(itemRepository.findByName(testItem.getName())).thenReturn(testItem);
        when(reviewRepository.save(any(Review.class))).thenReturn(testReview);

        Review savedReview = reviewService.addReview(
                testUser.getEmail(), testUser.getPhoneNumber(), testUser.getPassword(),
                testItem.getName(), testReview.getRating(), testReview.getComment());

        assertNotNull(savedReview);
        assertEquals(savedReview.getRating(), testReview.getRating());
        assertEquals(savedReview.getComment(), testReview.getComment());
        verify(itemRepository, times(1)).save(testItem); // Ensure the item is saved with the review
    }

    @Test
    public void testAddReviewInvalidUser() {
        when(userRepository.findUserByEmail(testUser.getEmail())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.addReview(
                    testUser.getEmail(), testUser.getPhoneNumber(), testUser.getPassword(),
                    testItem.getName(), testReview.getRating(), testReview.getComment());
        });

        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    public void testGetReviewsForItem() {
        when(itemRepository.findByName(testItem.getName())).thenReturn(testItem);

        List<Review> reviews = reviewService.getReviewsForItem(testItem.getName());

        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
        assertEquals(1, reviews.size());
        assertEquals(testReview.getComment(), reviews.get(0).getComment());
    }

    @Test
    public void testGetReviewsForItemNotFound() {
        when(itemRepository.findByName("Non-Existent Item")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.getReviewsForItem("Non-Existent Item");
        });

        assertEquals("Item not found: Non-Existent Item", exception.getMessage());
    }

    @Test
    public void testDeleteReview() {
        when(reviewRepository.findById(testReview.getId())).thenReturn(Optional.of(testReview));

        reviewService.deleteReview(testReview.getId());

        verify(reviewRepository, times(1)).delete(testReview);
    }

    @Test
    public void testDeleteReviewNotFound() {
        when(reviewRepository.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.deleteReview(999);
        });

        assertEquals("Review not found with ID: 999", exception.getMessage());
    }
}
