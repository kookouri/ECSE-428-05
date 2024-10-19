package com.controllers;

import java.util.*;

import com.dto.ItemDTO;
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
import org.springframework.web.bind.annotation.RestController;

import com.model.Item;
import com.services.ItemService;

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

    //--------------------------// Create Item //--------------------------//
    @PostMapping(value = {"/items", "/items/", "/public/items"})
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO item) {
        try {
            Item createdItem = itemService.createItem(item.getName(), item.getPrice(), item.getDescription(), item.getMcGillMart());
            return new ResponseEntity<ItemDTO>(new ItemDTO(createdItem), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ItemDTO>(new ItemDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------// Update Item //--------------------------//
    @PutMapping(value = {"/items/{id}", "/items/{id}/"})
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Integer id, @RequestBody ItemDTO item) {
        try {
            Item updatedItem = itemService.updateItem(id, item.getName(), item.getPrice(), item.getDescription(), item.getMcGillMart());
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
}
