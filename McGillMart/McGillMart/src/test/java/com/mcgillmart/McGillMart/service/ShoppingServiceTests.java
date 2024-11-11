package com.mcgillmart.McGillMart.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.repositories.ItemRepository;
import com.mcgillmart.McGillMart.repositories.McGillMartRepository;
import com.mcgillmart.McGillMart.repositories.UserRepository;
import com.mcgillmart.McGillMart.services.McGillMartService;
import com.mcgillmart.McGillMart.services.UserService;

@SpringBootTest
public class ShoppingServiceTests {
    @Mock
    private ItemRepository itemRepository;
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private McGillMartRepository mcgillMartRepository;

    @InjectMocks
    private UserService userService;

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

    
    //--------------------------// Create User Test //--------------------------//

    @Test
    public void testGetShoppingCart() {
        // TODO: implement
    }

    @Test
    public void testGetTransactions() {
        // TODO: implement
    }
}
