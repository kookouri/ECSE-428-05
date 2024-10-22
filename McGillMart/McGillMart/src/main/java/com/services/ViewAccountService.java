package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.User;

import jakarta.transaction.Transactional;

/**
 * A class containing services pertaining to viewing the user's account
 * Most logic is identical to UserService aside from a few input validations
 */
@Service
public class ViewAccountService{
	
	@Autowired
	private UserService userService;
	
	/**
	 * Provides an additional check on top of the functionalities provided by UserService
	 * Should be used only when retrieving the logged in user's account details
	 * @param loggedInUser the email of the logged in user
	 * @return the user's account
	 * @author Eric Z
	 */
	@Transactional
	public User viewUserProfile(String loggedInUser) {
		if (loggedInUser == null || loggedInUser.equals("")) {
			throw new IllegalArgumentException("User not logged in");
		}
		User user = userService.findUserByEmail(loggedInUser);
		return user;
	}
}