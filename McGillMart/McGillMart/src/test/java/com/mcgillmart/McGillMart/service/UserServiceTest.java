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
import com.mcgillmart.McGillMart.services.McGillMartService;
import com.mcgillmart.McGillMart.services.UserService;

@SpringBootTest
public class UserServiceTest {
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

        when(mcGillMartService.getMcGillMart()).thenReturn(mcgillMart);
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
        String email = "julia@mail.com";
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
        String email = "julia@mail.com";
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
		assertEquals("Empty fields for email, password, phone number, or name are not valid", e.getMessage());
    }

    //--------------------------// Update User Info Test //--------------------------//

    @Test 
    public void testUpdateUserInfo() {
        int id = 64;
        String email = "julia@mail.com";
        String password = "oldpassword";
        String name = "Lina";
        String phoneNumber = "333-333-3333";
        
        User julia = new User(email, name, password, phoneNumber, 
            toList(mcgillMartRepository.findAll()).get(0));
        
        
        when(userRepository.save(any(User.class))).thenReturn(julia);
        when(userRepository.findUserById(id)).thenReturn(julia);

        User user = userService.updateUser(id, "new@mail.com", "New Name", "newPassword", "098-765-4321");

        assertNotNull(user);
        assertEquals("new@mail.com", user.getEmail());
        assertEquals("New Name", user.getName());
        assertEquals("newPassword", user.getPassword());
        assertEquals("098-765-4321", user.getPhoneNumber());

    }

    @Test
    public void testUpdateUserWithSameEmail() {
        int id = 69;
        String email = "same@mail.com";
        String name = "Old Name";
        String password = "oldPassword";
        String phoneNumber ="987-654-3210";

        User userx = new User(email, name, password, phoneNumber, 
        toList(mcgillMartRepository.findAll()).get(0));
    
        when(userRepository.save(any(User.class))).thenReturn(userx);
        when(userRepository.findUserById(id)).thenReturn(userx);

        User user1 = userService.updateUser(id, "same@mail.com", "Updated Name", "updatedPassword", "987-654-3210");
        
        assertNotNull(user1);
        assertEquals("same@mail.com", user1.getEmail()); 
        assertEquals("Updated Name", user1.getName());
        assertEquals("updatedPassword", user1.getPassword());
        assertEquals("987-654-3210", user1.getPhoneNumber());
    }

    //to be fixed
    @Test
    public void testUpdateUserWithNonExistentId() {

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(999, "new@mail.com", "New Name", "newPassword", "098-765-4321");
        });

        assertNotNull(e);
        assertEquals("There is no user with ID 999.", e.getMessage());
    }

    @Test
    public void testUpdateUserWithSamePhoneNumber() {
        int id =77;
        String email = "user6@mail.com";
        String name = "User Six";
        String password = "password";
        String phoneNumber = "484-444-4444";

        User lina = new User(email, name, password, phoneNumber, 
        toList(mcgillMartRepository.findAll()).get(0));
    
        
        when(userRepository.save(any(User.class))).thenReturn(lina);
        when(userRepository.findUserById(id)).thenReturn(lina);

        User updatedUser = userService.updateUser(id, "new6@mail.com", "Updated Name", "newPassword", "484-444-4444");

        assertNotNull(updatedUser);
        assertEquals("new6@mail.com", updatedUser.getEmail());
        assertEquals("Updated Name", updatedUser.getName());
        assertEquals("newPassword", updatedUser.getPassword());
        assertEquals("484-444-4444", updatedUser.getPhoneNumber());

    }
    
    @Test
    public void testUpdateUserWithInvalidPassword() {
        int id = 70;
        String email = "user7@mail.com";
        String name = "User Seven";
        String password = "password";
        String phoneNumber = "777-777-7777";
        User user3 = userService.createUser(email, name, password, phoneNumber);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(id, "new7@mail.com", "User Seven", "short", "098-765-4321");
        });

        assertNotNull(e);
        assertEquals("The password needs to have 8 characters or more", e.getMessage());
    }

    @Test
    public void testUpdateUserWithEmptyField() {
        int id = 71;
        String email = "user8@mail.com";
        String name = "User Eight";
        String password = "password";
        String phoneNumber = "888-888-8888";
        userService.createUser(email, name, password, phoneNumber);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(id, "", "User Eight", "password", "098-765-4321");
            
        });

        assertNotNull(e);
        assertEquals("Empty fields for email, password, phone number, or name are not valid", e.getMessage());
    }
    
    @Test 
    public void testUpdateUserWithInvalidEmail() {
        int id = 72;
        
        String email = "user@mail.com";
        String name = "Name";
        String password = "password";
        String phoneNumber = "555-555-5555";
        User user5 = userService.createUser(email, name, password, phoneNumber);
       

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(id, "invalidEmail", "New Name", "newPassword", "098-765-4321");
        });

        assertNotNull(e);
        assertEquals("Email has to contain the character @", e.getMessage());
    }

    
    @Test 
    public void testUpdateUserWithInvalidPhoneNumber() {
        int id = 78;
        String email = "user@mail.com";
        String name = "Name";
        String password = "password";
        String phoneNumber = "555-555-5555";
        User user = userService.createUser(email, name, password, phoneNumber);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(id, "user@mail.com", "New Name", "newPassword", "invalidPhone");
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
