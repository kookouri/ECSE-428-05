package com.mcgillmart.McGillMart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.mcgillmart.McGillMart.util.ServiceUtils;
import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.repositories.McGillMartRepository;
import com.mcgillmart.McGillMart.repositories.UserRepository;
import com.mcgillmart.McGillMart.services.LoginService;
import com.mcgillmart.McGillMart.services.McGillMartService;
import com.mcgillmart.McGillMart.services.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class LoginServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private McGillMartRepository mcgillMartRepository;

    @InjectMocks
    private LoginService loginService;
    @InjectMocks
    private UserService userService;

    @Mock
    private McGillMartService mcGillMartService;

    @Mock
    private ServiceUtils serviceUtils;

    // @BeforeEach
    // public void setup() {
    //     MockitoAnnotations.openMocks(this);
    // }

    @AfterEach
    public void tearDown() {
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

    @Test
    public void testValidLogin() {
        //Setup
        String email = "testUser@gmail.com";
        String name = "testUser";
        String password = "testPass";
        String phoneNumber = "123-456-7890";

        //Act
        // user.setEmail(email);
        // user.setName(name);
        // user.setPassword(password);
        // user.setPhoneNumber(phoneNumber);

        // User user = new User(email, name, password, phoneNumber, mcGillMartService.getMcGillMart());

        User user = userService.createUser(email, name, password, phoneNumber);
        when(userRepository.save(any(User.class))).thenReturn(user);




        //Assert
        //when(userService.findUserByEmail("testUser@gmail.com")).thenReturn(user);

        boolean result = loginService.login("testUser@gmail.com", "testPass");

        assertEquals(true, result);
    }

    @Test
    public void testInvalidLoginUserDoesNotExist() {
        //Setup
        String email = "testUser@gmail.com";
        String name = "testUser";
        String password = "testPass";
        String phoneNumber = "123-456-7890";

        //Act
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);

        //Assert
        when(userService.findUserByEmail("invalidUser@gmail.com")).thenReturn(null);

        boolean result = loginService.login("invalidUser@gmail.com", "testPass");

        assertEquals(false, result);
    }
}
