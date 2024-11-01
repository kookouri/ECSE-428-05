package com.mcgillmart.McGillMart.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.model.User;
import com.mcgillmart.McGillMart.repositories.McGillMartRepository;
import com.mcgillmart.McGillMart.repositories.UserRepository;

/**
* <p>Service class in charge of managing users. It implements following use cases: </p>
* <p>Create, update, delete a user and login to account</p>
* @author Julia
*/
@Service("userService")
public class UserService {
    // Get user from the database, via Hibernate
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private McGillMartRepository mcGillMartRepository;

    @Autowired
    private McGillMartService mcGillMartService;


    //--------------------------// Create User //--------------------------//

    @Transactional
    public User createUser(String email, String name, String password, String phoneNumber) {
        validUserInfo(email, password, name, phoneNumber);
        uniqueEmail(email);
        
        User user = new User(email, name, password, phoneNumber, mcGillMartService.getMcGillMart());
        return userRepository.save(user);
    }

    //--------------------------// Update User //--------------------------//
    
    @Transactional
    public User updateUser(Integer id, String email, String name, String password, String phoneNumber) {
        validUserInfo(email, password, name, phoneNumber);

        User user = findUserById(id);

        // If it is the same email, then dont check if it is unique (it is not since it is used for this exact account), else verify
        if (!user.getEmail().equalsIgnoreCase(email)) {
            uniqueEmail(email);
            user.setEmail(email);
        }
        
        // If it is the same phone number, then dont check if it is unique (it is not since it is used for this exact account), else verify
        if (!user.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
            uniquePhoneNumber(phoneNumber);
            user.setPhoneNumber(phoneNumber);
        }
        

        user.setPassword(password);
        user.setName(name);
        user.setMcGillMart(mcGillMartService.getMcGillMart());
        return userRepository.save(user);
    }
    
    //--------------------------// Delete User //--------------------------//
    
    @Transactional
    public void deleteUser(Integer id) {
        User user = userRepository.findUserById(id);
        McGillMart mcgillMart = mcGillMartService.getMcGillMart();
        mcgillMart.removeUser(user);
        mcGillMartRepository.save(mcgillMart);

        user.delete();
        userRepository.save(user);
        userRepository.delete(user);
    }
    
    //--------------------------// Getters //--------------------------//

    @Transactional
	public User findUserById(Integer id) {
		User user = userRepository.findUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("There is no user with ID " + id + ".");
        }
		return user;
	}

    @Transactional
	public User findUserByEmail(String email) {
		User user = userRepository.findUserByEmail(email.toLowerCase());
        if (user == null) {
            throw new IllegalArgumentException("There is no user with email " + email + ".");
        }
		return user;
	}

    @Transactional
	public List<User> findAllUsers() {
		return toList(userRepository.findAll());
	}
    
    //--------------------------// Helper functions //--------------------------//

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

    //--------------------------// Input validations //--------------------------//

    private void validUserInfo(String email, String password, String name, String phoneNumber) {
        // Null or empty checks
        if (email == null || password == null || name == null || phoneNumber == null ||
            email.isEmpty() || password.isEmpty() || name.isEmpty() || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Empty fields for email, password, phone number, or name are not valid");
        }
    
        // Check for spaces in the email
        if (email.strip().contains(" ")) {
            throw new IllegalArgumentException("Email must not contain any spaces");
        }
    
        // Check if email contains '@' and prevent unsafe access
        int atIndex = email.indexOf("@");
        if (atIndex == -1) {
            throw new IllegalArgumentException("Email has to contain the character @");
        }
    
        // Check if the email starts with '@' (now safe since we know '@' exists)
        if (atIndex == 0) {
            throw new IllegalArgumentException("Invalid email");
        }
    
        // Validate period placement after the '@'
        boolean encounteredPeriod = false;
        for (int i = atIndex + 1; i < email.length(); i++) {
            if (email.charAt(i) == '.') {
                // Check if the period is immediately after '@' or at the end of the email
                if (i == atIndex + 1 || i == email.length() - 1) {
                    throw new IllegalArgumentException("Invalid email");
                }
                encounteredPeriod = true;
            }
        }
        if (!encounteredPeriod) {
            throw new IllegalArgumentException("Invalid email");
        }
    
        // Password length validation
        if (password.length() < 8) {
            throw new IllegalArgumentException("The password needs to have 8 characters or more");
        }
    
        // Validate phone number characters
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i);
            if (c != '-' && !Character.isDigit(c) && c != ' ') {
                throw new IllegalArgumentException("The phone number has invalid characters");
            }
        }
    }    

    private void uniqueEmail(String email) {
        if (userRepository.findUserByEmail(email) != null) {
            throw new IllegalArgumentException("The email is already associated to a user");
        }
    }

    private void uniquePhoneNumber(String phoneNumber) {
        List<User> users = userRepository.findByPhoneNumber(phoneNumber);
        if (!users.isEmpty()) {
            throw new IllegalArgumentException("The phone number is already associated to a user");
        }
    }
}
