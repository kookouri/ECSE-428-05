package com.mcgillmart.McGillMart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.mcgillmart.McGillMart.model.Item;
import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.model.Transaction;
import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.model.Item.Category;
import com.mcgillmart.McGillMart.repositories.ItemRepository;
import com.mcgillmart.McGillMart.repositories.McGillMartRepository;
import com.mcgillmart.McGillMart.repositories.TransactionRepository;
import com.mcgillmart.McGillMart.repositories.UserRepository;
import com.mcgillmart.McGillMart.services.McGillMartService;
import com.mcgillmart.McGillMart.services.ShoppingService;
import com.mcgillmart.McGillMart.services.UserService;

@SpringBootTest
public class ShoppingServiceTests {
    @Mock
    private ItemRepository itemRepository;
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private McGillMartRepository mcgillMartRepository;

    @Mock
    private TransactionRepository transactionRepository;
    
    @InjectMocks
    private UserService userService;

    @InjectMocks
    private ShoppingService shoppingService;

    @Mock
    private McGillMartService mcGillMartService;
    
    /**
     * Clear the sportcenter database before each test.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        mcgillMartRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }
    
    /**
     * Create and save a McGillMart instance before each test.
     */
    @BeforeEach
    public void createAndSaveMcGillMart() {
        McGillMart mcgillMart = new McGillMart();

        // Save McGillMart
        List<McGillMart> listMcGillMarts = new ArrayList<>();
        listMcGillMarts.add(mcgillMart);
        when(mcgillMartRepository.findAll()).thenReturn(listMcGillMarts);

        when(mcGillMartService.getMcGillMart()).thenReturn(mcgillMart);
    }

    
    //--------------------------// Shopping Test //--------------------------//

    @Test
    public void testGetShoppingCart() {
        // Set up test
        // First create a user that will hold the shopping cart
        int id = 64;
        String email = "julia@mail.com";
        String password = "secretPassword";
        String name = "Julia";
        String phoneNumber = "333-333-3333";
        
        User julia = new User(email, name, password, phoneNumber, 
            toList(mcgillMartRepository.findAll()).get(0));

        Item item1 = new Item("Socks", 6.00, "Soft white cotton socks", Category.Clothing, "", toList(mcgillMartRepository.findAll()).get(0));
        List<Item> shoppingCart = new ArrayList<>();
        shoppingCart.add(item1);

        julia.addShoppingCart(item1);

        when(userRepository.save(any(User.class))).thenReturn(julia);
        when(userRepository.findUserById(id)).thenReturn(julia);
        
        // Act
        List<Item> shoppingCartReceived = shoppingService.getShoppingCart(id);
        
        assertEquals(shoppingCart, shoppingCartReceived);
        assertEquals(shoppingCart.get(0), shoppingCartReceived.get(0));
        assertEquals(shoppingCart.size(), shoppingCartReceived.size());
    }

    
    @Test
    public void testGetShoppingCartTotal() {
        // Set up test
        // First create a user that will hold the shopping cart
        int id = 64;
        String email = "julia@mail.com";
        String password = "secretPassword";
        String name = "Julia";
        String phoneNumber = "333-333-3333";
        
        User julia = new User(email, name, password, phoneNumber, 
            toList(mcgillMartRepository.findAll()).get(0));

        Item item1 = new Item("Socks", 6.00, "Soft white cotton socks", Category.Clothing, "", toList(mcgillMartRepository.findAll()).get(0));
        Item item2 = new Item("Socks", 6.00, "Soft pink cotton socks", Category.Clothing, "", toList(mcgillMartRepository.findAll()).get(0));
        double shoppingCartTotal = item1.getPrice() + item2.getPrice();
        List<Item> shoppingCart = new ArrayList<>();
        shoppingCart.add(item1);
        shoppingCart.add(item2);

        julia.addShoppingCart(item1);
        julia.addShoppingCart(item2);

        when(userRepository.save(any(User.class))).thenReturn(julia);
        when(userRepository.findUserById(id)).thenReturn(julia);
        
        // Act
        int shoppingCartTotalReceived = shoppingService.getShoppingCartTotal(id);
        
        assertEquals(shoppingCartTotal, shoppingCartTotalReceived);
    }

    @Test
    public void testGetTransactions() {
        // Set up test
        // First create a user that will hold the history
        int id = 64;
        String email = "julia@mail.com";
        String password = "secretPassword";
        String name = "Julia";
        String phoneNumber = "333-333-3333";
        
        User julia = new User(email, name, password, phoneNumber, 
            toList(mcgillMartRepository.findAll()).get(0));
        

        double transactionTotal = 50.00;
        LocalDate transactionDate = LocalDate.now();
        String transactionDesc = "Socks, Hats, USB";
        julia.addHistory(transactionTotal, transactionDate, transactionDesc);

        when(userRepository.save(any(User.class))).thenReturn(julia);

        when(userRepository.findUserById(id)).thenReturn(julia);
        
        // Act
        List<Transaction> historyReceive = shoppingService.getTransactions(id);
        
        assertEquals(transactionTotal, historyReceive.get(0).getAmount(), 0.001);
        assertEquals(transactionDate.toString(), historyReceive.get(0).getDateOfPurchase().toString());
        assertEquals(transactionDesc, historyReceive.get(0).getDescription());
    }

    @Test
    public void testCheckoutShoppingCart_Success() {
        // Set up test
        int userId = 0;
        String email = "jeff@mail.mcgill.ca";
        String password = "validPass@123";
        String name = "Jeff";
        String phoneNumber = "123-456-7890";

        McGillMart mcgillMart = toList(mcgillMartRepository.findAll()).get(0);
        User jeff = new User(email, name, password, phoneNumber, mcgillMart);
        jeff.setId(userId);

        // Create items
        Item item1 = new Item("ECSE hoodie", 50.0, "Hoodie for ECSE students", Category.Clothing, "nothing.com", mcgillMart);
        item1.setId(0);
        Item item2 = new Item("Desautels Pencil Case", 12.0, "Pencil case with Desautels brand", Category.Stationary, "nothing.com", mcgillMart);
        item2.setId(1);

        // Add items to user's shopping cart
        jeff.addShoppingCart(item1);
        jeff.addShoppingCart(item2);

        // Mock userRepository.save(any(User.class)) to return the user
        when(userRepository.save(any(User.class))).thenReturn(jeff);

        // Mock userRepository.findUserById(userId)
        when(userRepository.findUserById(userId)).thenReturn(jeff);

        // Mock userRepository.findById(userId)
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(jeff));
    
        // Mock transactionRepository.save(any(Transaction.class)) to return the transaction
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction transaction = invocation.getArgument(0);
            transaction.setId(1); // Set an ID
            return transaction;
        });

        // Call the method under test
        String message = shoppingService.checkoutShoppingCart(userId);

        // Verify the message
        assertEquals("Successfully checked-out the shopping cart", message);

        // Verify that the shopping cart is empty
        assertEquals(0, jeff.getShoppingCart().size());

        // Verify that a transaction was created and added to user's history
        assertEquals(1, jeff.getHistory().size());
        Transaction transaction = jeff.getHistory().get(0);
        assertEquals(62.0, transaction.getAmount(), 0.001); // Total amount of items
        assertEquals("Purchase of 2 items.", transaction.getDescription());
        assertEquals(jeff, transaction.getUser());
    }

    @Test
    public void testCheckoutShoppingCart_EmptyCart() {
        // Set up test
        int userId = 0;
        String email = "jeff@mail.mcgill.ca";
        String password = "validPass@123";
        String name = "Jeff";
        String phoneNumber = "123-456-7890";

        McGillMart mcgillMart = toList(mcgillMartRepository.findAll()).get(0);
        User jeff = new User(email, name, password, phoneNumber, mcgillMart);
        jeff.setId(userId);

        // User has an empty shopping cart

        // Mock userRepository.save(any(User.class)) to return the user
        when(userRepository.save(any(User.class))).thenReturn(jeff);

        // Mock userRepository.findUserById(userId)
        when(userRepository.findUserById(userId)).thenReturn(jeff);

        // Mock userRepository.findById(userId)
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(jeff));

        // Call checkoutShoppingCart, it should throw an exception
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            shoppingService.checkoutShoppingCart(userId);
        });

        // Verify that the exception message matches the expected error
        assertEquals("Empty shopping cart: Item amount must be larger than 0.", exception.getMessage());

        // Verify that the shopping cart is still empty
        assertEquals(0, jeff.getShoppingCart().size());

        // Verify that no transaction was added to user's history
        assertEquals(0, jeff.getHistory().size());
    }
    //--------------------------// Helper functions //--------------------------//

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
