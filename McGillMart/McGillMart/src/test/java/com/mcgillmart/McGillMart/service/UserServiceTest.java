package com.mcgillmart.McGillMart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.model.McGillMart;
import com.model.User;
import com.repositories.McGillMartRepository;
import com.repositories.UserRepository;
import com.services.UserService;

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
        
        User julia = new User(email, name, password, phoneNumber, toList(mcgillMartRepository.findAll()).get(0));

        when(userRepository.save(any(User.class))).thenReturn(julia);

        // Act
        User createdUser = userService.createUser(email, name, password, phoneNumber);
    
        // Assert
        assertNotNull(createdUser);
        assertEquals(email.toLowerCase(), createdUser.getEmail());
        assertEquals(password, createdUser.getPassword());
        assertEquals(name, createdUser.getName());
        assertEquals(phoneNumber, createdUser.getPhoneNumber());
        verify(userRepository, times(1)).save(any(User.class));
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
