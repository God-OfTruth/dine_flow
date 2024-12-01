package com.ritesh.dineflow.services;

import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.models.Restaurant;
import com.ritesh.dineflow.repositories.RestaurantRepository;
import com.ritesh.dineflow.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	public void createRestaurantEntry(Restaurant restaurant) {
		Restaurant previousRestaurant = restaurantRepository.findByName(restaurant.getName()).orElse(null);
		if (restaurant.getId() != null) {
			throw new ResourceAlreadyPresentException("Restaurant Already Present");
		}
		String currentUserId = SecurityUtils.getCurrentUserId();
		if (previousRestaurant != null && previousRestaurant.getOwnerId().equals(currentUserId)) {
			throw new ResourceAlreadyPresentException("Restaurant Already Present");
		}
		restaurant.setOwnerId(currentUserId);
		restaurantRepository.save(restaurant);
	}

	public void updateRestaurantEntry(Restaurant restaurant) {
		if (restaurant.getId() == null) {
			throw new ResourceNotFoundException("Restaurant not Found");
		}
		Restaurant previousRestaurant = restaurantRepository.findById(restaurant.getId()).orElse(null);
		if (previousRestaurant != null) {
			if (SecurityUtils.getCurrentUserId().equals(restaurant.getOwnerId())) {
				restaurantRepository.save(restaurant);
			} else {
				throw new AccessDeniedException("Ask Administrator");
			}
		}

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
		String currentUserId = SecurityUtils.getCurrentUserId();
		if (SecurityUtils.isCurrentUserInRole("ROLE_SUPER_ADMIN")) {
			return restaurantRepository.findAll();
		}
		return restaurantRepository.findByOwnerId(currentUserId);
	}
}
