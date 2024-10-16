package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.McGillMart;
import com.repositories.McGillMartRepository;
import com.repositories.UserRepository;

/*
* Service class in charge of managing accounts. It implements following use cases:
* Create, find, delete a McGillMart
* @author Julia Grenier
*/
@Service
public class McGillMartService {
    @Autowired
    private McGillMartRepository mcgillMartRepo;

    @Autowired
    private UserRepository userRepo;

    //--------------------------// Create Sport Center //--------------------------//

    @Transactional
	public McGillMart createMcGillMart() {
        if (getAllMcGillMarts().size() > 0) {
            throw new IllegalArgumentException("Sport center already exists.");
        }
		McGillMart createdMcGillMart = new McGillMart();
		mcgillMartRepo.save(createdMcGillMart);
		return createdMcGillMart;
	}

    //--------------------------// Delete Sport Center //--------------------------//

    @Transactional
    public void deleteMcGillMart() {
        getMcGillMart(); // Verify that there is at least one sport center
        userRepo.deleteAll();
    }

    //--------------------------// Getters Sport Center //--------------------------//
    @Transactional
    public List<McGillMart> getAllMcGillMarts() {
        return toList(mcgillMartRepo.findAll());
    }

    @Transactional
    public McGillMart getMcGillMart() {
        if (getAllMcGillMarts().size() == 0) {
            throw new IllegalArgumentException("No sport center exist");
        }
        return getAllMcGillMarts().get(0);
    }

    //--------------------------// Helper function //--------------------------//

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
