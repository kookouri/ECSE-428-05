package com.mcgillmart.McGillMart.controllers;

import java.util.*;
import java.util.stream.Collectors;

import com.mcgillmart.McGillMart.dto.ItemDTO;
import com.mcgillmart.McGillMart.dto.ReviewDTO;
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
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO item) {
        try {
            Item createdItem = itemService.createItem(item.getName(), item.getPrice(), item.getDescription(), item.getCategory());
            return new ResponseEntity<ItemDTO>(new ItemDTO(createdItem), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ItemDTO>(new ItemDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Update Item //--------------------------//
    @PutMapping(value = {"/items/{id}", "/items/{id}/"})
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Integer id, @RequestBody ItemDTO item) {
        try {
            Item updatedItem = itemService.updateItem(id, item.getName(), item.getPrice(), item.getDescription(), item.getCategory());
            return new ResponseEntity<ItemDTO>(new ItemDTO(updatedItem), HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ItemDTO>(new ItemDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<ItemDTO> findItemById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<ItemDTO>(new ItemDTO(itemService.findItemById(id)), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ItemDTO>(new ItemDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = {"/items", "/items/"})
    public ResponseEntity<List<ItemDTO>> findAllItems() {
        try {
            List<Item> items = itemService.findAllItems();
            List<ItemDTO> itemDTOs = ItemDTO.itemListToItemDTOList(items);
            return new ResponseEntity<List<ItemDTO>>(itemDTOs, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<List<ItemDTO>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Add Review to Item //--------------------------//
    @PostMapping(value = {"/items/{itemName}/reviews"})
    public ResponseEntity<ReviewDTO> addReviewToItem(
            @PathVariable String itemName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String password,
            @RequestParam int rating,
            @RequestParam String comment) {
        try {
            Review newReview = reviewService.addReview(email, phone, password, itemName, rating, comment);
            return new ResponseEntity<ReviewDTO>(new ReviewDTO(newReview), HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ReviewDTO>(new ReviewDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Get Reviews for Item //--------------------------//
    @GetMapping(value = {"/items/{itemName}/reviews"})
    public ResponseEntity<List<ReviewDTO>> getReviewsForItem(@PathVariable String itemName) {
        try {
            List<ReviewDTO> reviews = reviewService.getReviewsForItem(itemName).stream()
                    .map(ReviewDTO::new).collect(Collectors.toList());
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
