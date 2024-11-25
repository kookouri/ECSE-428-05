package com.mcgillmart.McGillMart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.Transaction;
import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.repositories.ItemRepository;
import com.mcgillmart.McGillMart.repositories.TransactionRepository;
import com.mcgillmart.McGillMart.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


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

    @Autowired
    private TransactionRepository transactionRepo;

    @Transactional(readOnly = true)
    public List<Item> getShoppingCart(Integer userID) {
        User user = userRepo.findUserById(userID);
        if (user != null) {
            return user.getShoppingCart();
        }
        else {
            throw new IllegalArgumentException("Invalid user ID: no user found.");
        }
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public String addItemToCart(Integer userID, Integer itemId) {
        Optional<User> user = userRepo.findById(userID);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Invalid user or item id.");
        }
        Optional<Item> item = itemRepo.findById(itemId);
        if (user.isPresent() && item.isPresent()) {
            user.get().addShoppingCart(item.get());
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
            user.get().removeShoppingCart(item.get());
            userRepo.save(user.get());
            return ("Item removed successfully");
        } else {
            throw new IllegalArgumentException("Invalid user or item id.");
        }
    }

    @Transactional
    public String emptyCart(Integer userID) {
        Optional<User> user = userRepo.findById(userID);
        if (user.isPresent()) {

            // Clear the shopping cart
            user.get().getShoppingCart().clear();

            // Save the user to persist the changes
            userRepo.save(user.get());

            return "Shopping cart has been emptied successfully.";
        } else {
            throw new IllegalArgumentException("Invalid user ID: no user found.");
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

        // Create a new transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(totalAmount);
        transaction.setDateOfPurchase(LocalDate.now());
        String description = "Items purchased: ";
        for (Item item : cartItems) {
            description += item.getName();
            description += ", ";
        }
        // Remove the last ','
        description = description.substring(0, description.length() - 2);
        transaction.setDescription(description);
        transaction.setUser(user);
        
        // Associate the transaction with the user's history
        user.addHistory(transaction);
        
        transactionRepo.save(transaction);
        userRepo.save(user);

        emptyCart(userID);

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
