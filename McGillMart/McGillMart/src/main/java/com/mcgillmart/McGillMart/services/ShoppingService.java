package com.mcgillmart.McGillMart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgillmart.McGillMart.repositories.ItemRepository;
import com.mcgillmart.McGillMart.repositories.UserRepository;
import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.Transaction;
import com.mcgillmart.McGillMart.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

/**
* <p>Service class in charge of managing transactions and the shopping. It implements following use cases: </p>
* <p>Generate transaction summary report for a user, checking out shopping cart, getting the shopping cart content</p>
* @author Julia
*/
@Service("shoppingService")
public class ShoppingService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ItemRepository itemRepo;

    @Transactional
    public List<Item> getShoppingCart(Integer userID) {
        User user = userRepo.findUserById(userID);
        if (user != null) {
            return user.getShoppingCart();
        }
        else {
            throw new IllegalArgumentException("Invalid user ID: no user found.");
        }
    }

    @Transactional
    public Integer getShoppingCartTotal(Integer userID) {
        User user = userRepo.findUserById(userID);
        if (user != null) {
            int total = 0;

            for (Item item : user.getShoppingCart()) {
                total += item.getPrice();
            }

            return total;
        }
        else {
            throw new IllegalArgumentException("Invalid user ID: no user found.");
        }
    }

    // TODO: Add shopping cart adding item functionalities - Ana

    // TODO: Add shopping cart editing items functionalities - Connor
    @Transactional
    public String addItemToCart(Integer userID, Integer itemId) {
        Optional<User> user = userRepo.findById(userID);
        Optional<Item> item = itemRepo.findById(itemId);
        if (user.isPresent() && item.isPresent()) {
            user.get().getShoppingCart().add(item.get());
            userRepo.save(user.get());
            return ("Item added successfully");
        } else {
            throw new IllegalArgumentException("Invalid user or item id.");
        }
    }

    @Transactional
    public String removeItemFromCart(Integer userID, Integer itemId) {
        Optional<User> user = userRepo.findById(userID);
        Optional<Item> item = itemRepo.findById(itemId);
        if (user.isPresent() && item.isPresent()) {
            user.get().getShoppingCart().remove(item.get());
            userRepo.save(user.get());
            return ("Item removed successfully");
        } else {
            throw new IllegalArgumentException("Invalid user or item id.");
        }
    }

    @Transactional
    public String checkoutShoppingCart(Integer userID) {
        User user = userRepo.findUserById(userID);

        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID: no user found.");
        }

        List<Item> cartItems = user.getShoppingCart();

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Empty shopping cart: Item amount must be larger than 0.");
        }

        double totalAmount = cartItems.stream().mapToDouble(Item::getPrice).sum();

        Transaction transaction = new Transaction();
        transaction.setAmount(totalAmount);
        transaction.setDateOfPurchase(LocalDate.now());
        transaction.setDescription("Purchase of " + cartItems.size() + " items.");
        transaction.setUser(user);

        user.getHistory().add(transaction);

        cartItems.clear();

        userRepo.save(user);
        return "Successfully checked-out the shopping cart";
    }

    @Transactional
    public List<Transaction> getTransactions(Integer userID) {
        User user = userRepo.findUserById(userID);
        if (user != null) {
            return user.getHistory();
        }
        else {
            throw new IllegalArgumentException("Invalid user ID: no user found.");
        }
    }
}
