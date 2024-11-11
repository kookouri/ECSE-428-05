package com.mcgillmart.McGillMart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mcgillmart.McGillMart.dto.ItemRequestDTO;
import com.mcgillmart.McGillMart.dto.ShoppingCartDTO;
import com.mcgillmart.McGillMart.dto.TransactionListDTO;
import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.services.ItemService;
import com.mcgillmart.McGillMart.services.ShoppingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>Controller class in charge of managing shopping actions.</p>
 * @author Julia
 */
@CrossOrigin(origins = "http://127.0.0.1:8087")
@RestController
public class ShoppingController {
    @Autowired
    ShoppingService shoppingService;

    @GetMapping(value={"/users/{id}/shoppingCart", "/users/{id}/shoppingCart"})
    public ResponseEntity<ShoppingCartDTO> getShoppingCart(@PathVariable Integer id) {
        try {
            return new ResponseEntity<ShoppingCartDTO>(new ShoppingCartDTO(shoppingService.getShoppingCart(id)), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ShoppingCartDTO>(new ShoppingCartDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value={"/users/{id}/shoppingCartTotal", "/users/{id}/shoppingCartTotal"})
    public ResponseEntity<Integer> getShoppingCartTotal(@PathVariable Integer id) {
        try {
            return new ResponseEntity<Integer>(shoppingService.getShoppingCartTotal(id), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Integer>(-1, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping(value={"/users/{id}/transactions", "/users/{id}/transactions"})
    public ResponseEntity<TransactionListDTO> getTransactions(@PathVariable Integer id) {
        try {
            return new ResponseEntity<TransactionListDTO>(new TransactionListDTO(shoppingService.getTransactions(id)), HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<TransactionListDTO>(new TransactionListDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value={"/users/{userId}/shoppingCart/items/{itemId}"})
    public ResponseEntity<String> addItemToCart(@PathVariable Integer userId, @PathVariable Integer itemId) {
        try {
            shoppingService.addItemToCart(userId, itemId);
            return new ResponseEntity<String>("Item added successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value={"/users/{userId}/shoppingCart/items/{itemId}"})
    public ResponseEntity<String> removeItemFromCart(@PathVariable Integer userId, @PathVariable Integer itemId) {
        try {
            shoppingService.removeItemFromCart(userId, itemId);
            return new ResponseEntity<String>("Item removed successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
