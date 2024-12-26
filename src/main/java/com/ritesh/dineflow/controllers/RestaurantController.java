package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.models.Restaurant;
import com.ritesh.dineflow.services.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Restaurants Controller")
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> addNewRestaurant(@RequestBody Restaurant restaurant) {
		if (restaurant.getId() == null) {
			restaurantService.createRestaurantEntry(restaurant);
		} else {
			restaurantService.updateRestaurantEntry(restaurant);
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping()
	public ResponseEntity<List<Restaurant>> getAllRestaurants() {
		return ResponseEntity.status(HttpStatus.OK).body(restaurantService.findAllRestaurants());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> getRestaurantById(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.OK).body(restaurantService.findByRestaurantId(id));
	}

	@GetMapping("by-name/{name}")
	public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable("name") String name) {
		return ResponseEntity.status(HttpStatus.OK).body(restaurantService.findByRestaurantName(name));
	}

	@GetMapping("by-owner/{ownerId}")
	public ResponseEntity<List<Restaurant>> getRestaurantsByOwnerId(@PathVariable("ownerId") String ownerId) {
		return ResponseEntity.status(HttpStatus.OK).body(restaurantService.findByOwnerId(ownerId));
	}

	@GetMapping("by-manager/{managerId}")
	public ResponseEntity<List<Restaurant>> getRestaurantsByManagerId(@PathVariable("managerId") String managerId) {
		return ResponseEntity.status(HttpStatus.OK).body(restaurantService.findByManagerId(managerId));
	}

	@GetMapping("by-staff/{staffId}")
	public ResponseEntity<List<Restaurant>> getRestaurantsByStaffId(@PathVariable("staffId") String staffId) {
		return ResponseEntity.status(HttpStatus.OK).body(restaurantService.findByStaffId(staffId));
	}
}
