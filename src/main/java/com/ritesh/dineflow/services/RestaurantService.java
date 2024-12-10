package com.ritesh.dineflow.services;

import com.ritesh.dineflow.exceptions.LicenseException;
import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.models.Menu;
import com.ritesh.dineflow.models.Restaurant;
import com.ritesh.dineflow.models.User;
import com.ritesh.dineflow.models.UserProfile;
import com.ritesh.dineflow.repositories.RestaurantRepository;
import com.ritesh.dineflow.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private MenuService menuService;

	public void createRestaurantEntry(Restaurant restaurant) {
		User currentUser = SecurityUtils.getCurrentUser();
		UserProfile userProfile = userProfileService.getById(currentUser.getProfileId());
		long currentRestaurantCount = findByOwnerId(currentUser.getId()).size();
		Restaurant previousRestaurant = restaurantRepository.findByName(restaurant.getName()).orElse(null);

		if (restaurant.getId() != null) {
			throw new ResourceAlreadyPresentException("Restaurant Already Present");
		}

		if (previousRestaurant != null && previousRestaurant.getOwnerId().equals(currentUser.getId())) {
			throw new ResourceAlreadyPresentException("Restaurant Already Present");
		}

		// Checks is User can Create more Restaurants.
		if (userProfile.getRestaurantsLicensed() == currentRestaurantCount) {
			throw new LicenseException("Reached Restaurant limit");
		}

		restaurant.setOwnerId(currentUser.getId());
		restaurantRepository.save(restaurant);
	}

	public void updateRestaurantEntry(Restaurant restaurant) {
		if (restaurant.getId() == null) {
			throw new ResourceNotFoundException("Restaurant not Found");
		}
		Restaurant previousRestaurant = restaurantRepository.findById(restaurant.getId()).orElse(null);
		if (previousRestaurant != null) {
			if (SecurityUtils.getCurrentUserId().equals(previousRestaurant.getOwnerId())) {
				Set<String> menuIds = new HashSet<>(restaurant.getMenuIds());
				menuIds.addAll(previousRestaurant.getMenuIds());
				if (!menuIds.isEmpty()) {
					menuIds.forEach(id -> {
						Menu menu = menuService.getMenuById(id);
						Set<String> restaurantIds = new HashSet<>(menu.getRestaurantIds());
						restaurantIds.add(restaurant.getId());
						menu.setRestaurantIds(restaurantIds);
						menuService.updateMenuEntry(menu);
					});
				}
				restaurant.setMenuIds(menuIds.stream().toList());
				restaurant.setOwnerId(previousRestaurant.getOwnerId());
				restaurantRepository.save(restaurant);
			} else {
				throw new AccessDeniedException("Ask Administrator");
			}
		}

	}

	public Restaurant findByRestaurantId(String id) {
		return restaurantRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));
	}

	public Restaurant findByRestaurantName(String name) {
		return restaurantRepository.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));
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
