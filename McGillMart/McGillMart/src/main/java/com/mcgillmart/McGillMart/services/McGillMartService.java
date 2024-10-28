package com.mcgillmart.McGillMart.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mcgillmart.McGillMart.model.McGillMart;
import com.mcgillmart.McGillMart.repositories.McGillMartRepository;
import com.mcgillmart.McGillMart.repositories.UserRepository;

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

    //--------------------------// Create McGillMart //--------------------------//

    @Transactional
	public McGillMart createMcGillMart() {
        if (getAllMcGillMarts().size() > 0) {
            throw new IllegalArgumentException("McGillMart already exists.");
        }
		McGillMart createdMcGillMart = new McGillMart();
		mcgillMartRepo.save(createdMcGillMart);
		return createdMcGillMart;
	}

    //--------------------------// Delete McGillMart //--------------------------//

    @Transactional
    public void deleteMcGillMart() {
        getMcGillMart(); // Verify that there is at least one McGillMart
        userRepo.deleteAll();
    }

    //--------------------------// Getters McGillMart //--------------------------//
    @Transactional
    public List<McGillMart> getAllMcGillMarts() {
        return toList(mcgillMartRepo.findAll());
    }

    @Transactional
    public McGillMart getMcGillMart() {
        if (getAllMcGillMarts().size() == 0) {
            throw new IllegalArgumentException("No McGillMart exist");
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
