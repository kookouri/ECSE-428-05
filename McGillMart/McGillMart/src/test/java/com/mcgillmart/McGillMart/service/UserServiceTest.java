package com.mcgillmart.McGillMart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.repositories.McGillMartRepository;
import com.mcgillmart.McGillMart.repositories.UserRepository;
import com.mcgillmart.McGillMart.services.UserService;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private McGillMartRepository mcgillMartRepository;

    @InjectMocks
    private UserService userService;
    
    /**
     * Clear the sportcenter database before each test.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        userRepository.deleteAll();
        mcgillMartRepository.deleteAll();
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
    }

    
    //--------------------------// Create User Test //--------------------------//

    @Test
    public void testCreateValidUser() {
        // Set up test
        String email = "julia@mail.com";
        String password = "secretPassword";
        String name = "Julia";
        String phoneNumber = "333-333-3333";
        
        User julia = new User(email, name, password, phoneNumber, 
            toList(mcgillMartRepository.findAll()).get(0));

        when(userRepository.save(any(User.class))).thenReturn(julia);

        // Act
        User createdUser = userService.createUser(email, name, password, phoneNumber);
    
        // Assert
        assertNotNull(createdUser);
        assertEquals(email.toLowerCase(), createdUser.getEmail());
        assertEquals(password, createdUser.getPassword());
        assertEquals(name, createdUser.getName());
        assertEquals(phoneNumber, createdUser.getPhoneNumber());
        verify(userRepository, 
            times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUserWithInvalidEmail() {
        // Set up test
        String email = "julia";
        String password = "secretPassword";
        String name = "Julia";
        String phoneNumber = "333-333-3333";

        IllegalArgumentException e = 
            assertThrows(IllegalArgumentException.class, 
            () -> userService.createUser(email, name, password, phoneNumber));

		assertNotNull(e);
		assertEquals("Email has to contain the character @", e.getMessage());
    }

    @Test
    public void testCreateUserWithInvalidPhoneNumber() {
        // Set up test
        String email = "julia";
        String password = "secretPassword";
        String name = "Julia";
        String phoneNumber = "333-333-3333a";

        IllegalArgumentException e = 
            assertThrows(IllegalArgumentException.class, 
            () -> userService.createUser(email, name, password, phoneNumber));

		assertNotNull(e);
		assertEquals("The phone number has invalid characters", e.getMessage());
    }
    
    @Test
    public void testCreateUserWithInvalidPassword() {
        // Set up test
        String email = "julia";
        String password = "tiny";
        String name = "Julia";
        String phoneNumber = "333-333-3333";

        IllegalArgumentException e = 
            assertThrows(IllegalArgumentException.class, 
            () -> userService.createUser(email, name, password, phoneNumber));

		assertNotNull(e);
		assertEquals("The password needs to have 8 characters or more", 
            e.getMessage());
    }
    
    @Test
    public void testCreateUserWithEmptyField() {
        // Set up test
        String email = "";
        String password = "tiny";
        String name = "Julia";
        String phoneNumber = "333-333-3333";

        IllegalArgumentException e = 
            assertThrows(IllegalArgumentException.class, 
            () -> userService.createUser(email, name, password, phoneNumber));

		assertNotNull(e);
		assertEquals("Empty fields for email, password, " +
            "phone number or name are not valid", e.getMessage());
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
