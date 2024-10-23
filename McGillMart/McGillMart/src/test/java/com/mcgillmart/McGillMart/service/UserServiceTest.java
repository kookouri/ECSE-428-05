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
import java.util.Optional;

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

    //--------------------------// Update User Info Test //--------------------------//

    @Test 
    public void testUpdateUserInfo() {
        String email = "";
        String password = "oldpassword";
        String name = "Lina";
        String phoneNumber = "333-333-3333";
        
        User user = userService.createUser(email, name, password, phoneNumber);
        int userId = user.getId();
        user = userService.updateUser(userId, "new@mail.com", "New Name", "newPassword", "098-765-4321");

        assertNotNull(user);
        assertEquals("new@mail.com", user.getEmail());
        assertEquals("New Name", user.getName());
        assertEquals("newPassword", user.getPassword());
        assertEquals("098-765-4321", user.getPhoneNumber());

    }

    @Test
    public void testUpdateUserWithSameEmail() {
        
        String email = "same@mail.com";
        String name = "Old Name";
        String password = "oldPassword";
        String phoneNumber ="987-654-3210";
        User user = userService.createUser(email, name, password, phoneNumber);

        user = userService.updateUser(user.getId(), "same@mail.com", "Updated Name", "updatedPassword", "987-654-3210");
    
        assertNotNull(user);
        assertEquals("same@mail.com", user.getEmail()); 
        assertEquals("Updated Name", user.getName());
        assertEquals("updatedPassword", user.getPassword());
        assertEquals("987-654-3210", user.getPhoneNumber());
    }

    @Test
    public void testUpdateUserWithNonExistentId() {

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(999, "new@mail.com", "New Name", "newPassword", "098-765-4321");
        });

        assertNotNull(e);
        assertEquals("User not found with the given ID", e.getMessage());
    }

    @Test
    public void testUpdateUserWithSamePhoneNumber() {

        String email = "user6@mail.com";
        String name = "User Six";
        String password = "password";
        String phoneNumber = "444-444-4444";
        User user = userService.createUser(email, name, password, phoneNumber);

        user = userService.updateUser(user.getId(), "new6@mail.com", "Updated Name", "newPassword", "444-444-4444");

        assertNotNull(user);
        assertEquals("new6@mail.com", user.getEmail());
        assertEquals("Updated Name", user.getName());
        assertEquals("newPassword", user.getPassword());
        assertEquals("444-444-4444", user.getPhoneNumber());
    }
    
    // not yet implemented in UserService updateUser() method
    @Test
    public void testUpdateUserWithInvalidPassword() {

        String email = "user7@mail.com";
        String name = "User Seven";
        String password = "password";
        String phoneNumber = "777-777-7777";
        User user = userService.createUser(email, name, password, phoneNumber);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(user.getId(), "new7@mail.com", "User Seven", "short", "098-765-4321");
        });

        assertNotNull(e);
        assertEquals("The password needs to have 8 characters or more", e.getMessage());
    }

    // not yet implemented in UserService updateUser() method
    @Test
    public void testUpdateUserWithEmptyField() {

        String email = "user8@mail.com";
        String name = "User Eight";
        String password = "password";
        String phoneNumber = "888-888-8888";
        User user = userService.createUser(email, name, password, phoneNumber);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(user.getId(), "", "User Eight", "password", "098-765-4321");
        });

        assertNotNull(e);
        assertEquals("Empty fields for email, password, phone number or name are not valid", e.getMessage());
    }

    // not yet implemented in UserService updateUser() method
    @Test 
    public void testUpdateUserWithInvalidEmail() {

        String email = "user@mail.com";
        String name = "Name";
        String password = "password";
        String phoneNumber = "555-555-5555";
        User user = userService.createUser(email, name, password, phoneNumber);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(user.getId(), "invalidEmail", "New Name", "newPassword", "098-765-4321");
        });

        assertNotNull(e);
        assertEquals("Email has to contain the character @", e.getMessage());
    }

    // not yet implemented in UserService updateUser() method
    @Test 
    public void testUpdateUserWithInvalidPhoneNumber() {

        String email = "user@mail.com";
        String name = "Name";
        String password = "password";
        String phoneNumber = "555-555-5555";
        User user = userService.createUser(email, name, password, phoneNumber);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(user.getId(), "user@mail.com", "New Name", "newPassword", "invalidPhone");
        });

        assertNotNull(e);
        assertEquals("The phone number has invalid characters", e.getMessage());
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
