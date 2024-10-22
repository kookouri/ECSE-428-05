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

import com.model.McGillMart;
import com.model.User;
import com.services.UserService;
import com.services.ViewAccountService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ViewAccountServiceTest {
	@Mock
	private UserService userService;
	@InjectMocks
	private ViewAccountService viewAccService;
	
	@Test
	public void testViewAccountWhenLoggedIn() {
		User user = new User("joe@gmail.com", "joe", "validPass@123", "123-456-7890", null, new McGillMart()); 
		lenient().when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
		
		User account = viewAccService.viewUserProfile(user.getEmail());
		
		assertNotNull(account);
		assertEquals(user.getEmail(), account.getEmail());
		assertEquals(user.getName(), account.getName());
		assertEquals(user.getPhoneNumber(), account.getPhoneNumber());
		verify(userService, times(1)).findUserByEmail(user.getEmail());
	}
	
	@Test
	public void testViewAccountWhenLoggedOutFirstScenario() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> viewAccService.viewUserProfile(null));
		assertNotNull(e);
		assertEquals("User not logged in", e);
	}
	
	@Test
	public void testViewAccountWhenLoggedOutSecondScenario() {
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> viewAccService.viewUserProfile(""));
		assertNotNull(e);
		assertEquals("User not logged in", e);
	}
	
}
