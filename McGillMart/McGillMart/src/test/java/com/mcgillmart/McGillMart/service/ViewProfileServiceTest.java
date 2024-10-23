package com.mcgillmart.McGillMart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.services.UserService;
import com.mcgillmart.McGillMart.services.ViewProfileService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ViewProfileServiceTest {
	@Mock
	private UserService userService;
	@InjectMocks
	private ViewProfileService viewProfileService;
	
	@Test
	public void testViewProfileWhenLoggedIn() {
		User user = new User("joe@gmail.com", "joe", "validPass@123", "123-456-7890", new McGillMart()); 
		lenient().when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
		
		User account = viewProfileService.viewUserProfile(user.getEmail());
		
		assertNotNull(account);
		assertEquals(user.getEmail(), account.getEmail());
		assertEquals(user.getName(), account.getName());
		assertEquals(user.getPhoneNumber(), account.getPhoneNumber());
		verify(userService, times(1)).findUserByEmail(user.getEmail());
	}
	
	@Test
	public void testViewProfileWhenLoggedOutFirstScenario() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> viewProfileService.viewUserProfile(null));
		assertNotNull(e);
		assertEquals("User not logged in", e.getMessage());
	}
	
	@Test
	public void testViewProfileWhenLoggedOutSecondScenario() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> viewProfileService.viewUserProfile(""));
		assertNotNull(e);
		assertEquals("User not logged in", e.getMessage());
	}
	
}
