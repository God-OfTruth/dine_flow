package com.ritesh.dineflow.services;

import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.models.Restaurant;
import com.ritesh.dineflow.repositories.RestaurantRepository;
import com.ritesh.dineflow.utils.SecurityUtils;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	public void createRestaurantEntry(Restaurant restaurant) {
		if (restaurant.getId() != null) {
			throw new ResourceAlreadyPresentException("Restaurant Already Present");
		}
		String currentUserId = SecurityUtils.getCurrentUserId();
		restaurant.setOwnerId(currentUserId);
		restaurantRepository.save(restaurant);
	}

	public void updateRestaurantEntry(Restaurant restaurant) {
		if (restaurant.getId() == null) {
			throw new ResourceNotFoundException("Restaurant not Found");
		}
		restaurantRepository.save(restaurant);
	}

	public Restaurant findByRestaurantId(String id) {
		return restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));
	}

	public Restaurant findByRestaurantName(String name) {
		return restaurantRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));
	}

	public List<Restaurant> findByOwnerId(String ownerId) {
		return restaurantRepository.findByOwnerId(ownerId);
	}

	public List<Restaurant> findByManagerId(String managerId) {
		return restaurantRepository.findByManagersIn(managerId);
	}

	public List<Restaurant> findByStaffId(String staffId) {
		return restaurantRepository.findByStaffsIn(staffId);
	}

	public List<Restaurant> findAllRestaurants() {
		return restaurantRepository.findAll();
	}
}
