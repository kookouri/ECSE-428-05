package com.mcgillmart.McGillMart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.services.LoginService;
import com.mcgillmart.McGillMart.services.UserService;

@SpringBootTest
public class LoginServiceTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        // Clear any setup if necessary
    }

    @Test
    public void testSuccessfulLogin() {
        String email = "test@mail.com";
        String password = "password123";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userService.findUserByEmail(email)).thenReturn(user);

        boolean result = loginService.login(email, password);

        assertEquals(true, result);
        verify(userService, times(1)).findUserByEmail(email);
    }

    @Test
    public void testLoginWithEmptyEmail() {
        String email = "";
        String password = "password123";

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            loginService.login(email, password);
        });

        assertNotNull(e);
        assertEquals("Please enter both email and password", e.getMessage());
    }

    @Test
    public void testLoginWithEmptyPassword() {
        String email = "test@mail.com";
        String password = "";

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            loginService.login(email, password);
        });

        assertNotNull(e);
        assertEquals("Please enter both email and password", e.getMessage());
    }

    @Test
    public void testLoginWithInvalidEmail() {
        String email = "testmail.com";
        String password = "password123";

        when(userService.findUserByEmail(email)).thenThrow(IllegalArgumentException.class);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            loginService.login(email, password);
        });

        assertNotNull(e);
        assertEquals("Email not found, please register", e.getMessage());
    }

    @Test
    public void testLoginWithNonExistentUser() {
        String email = "nonexistent@mail.com";
        String password = "password123";

        when(userService.findUserByEmail(email)).thenThrow(IllegalArgumentException.class);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            loginService.login(email, password);
        });

        assertNotNull(e);
        assertEquals("Email not found, please register", e.getMessage());
    }

    @Test
    public void testLoginWithIncorrectPassword() {
        String email = "test@mail.com";
        String password = "password123";
        User user = new User();
        user.setEmail(email);
        user.setPassword("differentPassword");

        when(userService.findUserByEmail(email)).thenReturn(user);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            loginService.login(email, password);
        });

        assertNotNull(e);
        assertEquals("Incorrect password, please try again", e.getMessage());
    }
}
