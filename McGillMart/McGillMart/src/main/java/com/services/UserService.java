package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.McGillMart;
import com.model.User;
import com.repositories.McGillMartRepository;
import com.repositories.UserRepository;

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
        validUserInfo(email, password, name);
        uniqueEmail(email);
        
        User user = new User(email, name, password, phoneNumber, mcGillMartService.getMcGillMart());
        return userRepository.save(user);
    }

    //--------------------------// Update User //--------------------------//
    
    @Transactional
    public User updateUser(Integer id, String email, String name, String password, String phoneNumber) {
        validUserInfo(email, password, name);

        User user = findUserById(id);

        // If it is the same email, then dont check if it is unique (it is not since it is used for this exact account), else verify
        if (!user.getEmail().equalsIgnoreCase(email)) {
            uniqueEmail(email);
            user.setEmail(email);
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

    private void validUserInfo(String email, String password, String name) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            throw new IllegalArgumentException("Empty fields for email, password or name are not valid");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email has to contain the character @");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("The password needs to have 8 characters or more");
        }
    }

    private void uniqueEmail(String email) {
        if (userRepository.findUserByEmail(email) != null) {

            throw new IllegalArgumentException("The email is already associated to an account");
        }
    }
}