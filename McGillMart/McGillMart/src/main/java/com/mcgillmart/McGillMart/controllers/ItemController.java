package com.mcgillmart.McGillMart.controllers;

import java.util.*;
import java.util.stream.Collectors;

import com.mcgillmart.McGillMart.dto.ItemRequestDTO;
import com.mcgillmart.McGillMart.dto.ItemResponseDTO;
import com.mcgillmart.McGillMart.dto.ReviewRequestDTO;
import com.mcgillmart.McGillMart.dto.ReviewResponseDTO;
import com.mcgillmart.McGillMart.dto.UserRequestDTO;
import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.Review;
import com.mcgillmart.McGillMart.services.ItemService;
import com.mcgillmart.McGillMart.services.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Controller class in charge of managing items. It implements the following use cases: </p> 
 * <p>Create, update, read and delete an item </p>
 * @author Anastasiia
 */
@CrossOrigin(origins = "http://127.0.0.1:8087")
@RestController
public class ItemController {
    @Autowired
    ItemService itemService;

    @Autowired
    ReviewService reviewService;

    //--------------------------// Create Item //--------------------------//
    @PostMapping(value = {"/items", "/items/", "/public/items"})
    public ResponseEntity<ItemResponseDTO> createItem(@RequestBody ItemRequestDTO item) {
        try {
            Item createdItem = itemService.createItem(item.getName(), item.getPrice(), item.getDescription(), item.getCategory(), item.getUrl());
            return new ResponseEntity<ItemResponseDTO>(new ItemResponseDTO(createdItem), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ItemResponseDTO>(new ItemResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Update Item //--------------------------//
    @PutMapping(value = {"/items/{id}", "/items/{id}/"})
    public ResponseEntity<ItemResponseDTO> updateItem(@PathVariable Integer id, @RequestBody ItemRequestDTO item) {
        try {
            Item updatedItem = itemService.updateItem(id, item.getName(), item.getPrice(), item.getDescription(), item.getCategory(), item.getUrl());
            return new ResponseEntity<ItemResponseDTO>(new ItemResponseDTO(updatedItem), HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ItemResponseDTO>(new ItemResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Delete Item //--------------------------//
    @DeleteMapping(value = {"/items/{id}", "/items/{id}/"})
    public ResponseEntity<String> deleteItem(@PathVariable Integer id) {
        try {
            itemService.deleteItem(id);
            return new ResponseEntity<String>("Deleted", HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //--------------------------// Getters //--------------------------//
    @GetMapping(value = {"/items/{id}", "/items/{id}/"})
    public ResponseEntity<ItemResponseDTO> findItemById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<ItemResponseDTO>(new ItemResponseDTO(itemService.findItemById(id)), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ItemResponseDTO>(new ItemResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = {"/items", "/items/"})
    public ResponseEntity<List<ItemResponseDTO>> findAllItems() {
        try {
            List<Item> items = itemService.findAllItems();
            List<ItemResponseDTO> ItemResponseDTOs = ItemResponseDTO.itemListToItemResponseDTOList(items);
            return new ResponseEntity<List<ItemResponseDTO>>(ItemResponseDTOs, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<List<ItemResponseDTO>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Add Review to Item //--------------------------//
    @PostMapping(value = {"/items/{itemName}/reviews", "/items/{itemName}/reviews/"})
    public ResponseEntity<ReviewResponseDTO> addReviewToItem(
            @PathVariable String itemName, 
            @RequestParam UserRequestDTO user,
            @RequestParam ReviewRequestDTO review) {
        try {
            Review newReview = 
                reviewService.addReview(
                    user.getEmail(), user.getPhoneNumber(), user.getPassword(), 
                    review.getItemName(), review.getRating(), review.getComment());
            return new ResponseEntity<ReviewResponseDTO>(new ReviewResponseDTO(newReview), HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ReviewResponseDTO>(new ReviewResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Get Reviews for Item //--------------------------//
    @GetMapping(value = {"/items/{itemName}/reviews"})
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsForItem(@PathVariable String itemName) {
        try {
            List<ReviewResponseDTO> reviews = reviewService.getReviewsForItem(itemName).stream()
                    .map(ReviewResponseDTO::new).collect(Collectors.toList());
            return new ResponseEntity<>(reviews, HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //--------------------------// Delete Review //--------------------------//
    @DeleteMapping(value = {"/reviews/{reviewId}"})
    public ResponseEntity<String> deleteReview(@PathVariable Integer reviewId) {
        try {
            reviewService.deleteReview(reviewId);
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
