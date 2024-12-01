package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.models.Restaurant;
import com.ritesh.dineflow.services.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Restaurants Controller")
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	@PostMapping()
	public ResponseEntity<String> addNewRestaurant(Restaurant restaurant) {
		restaurantService.createRestaurantEntry(restaurant);
		return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant Created Successfully");
	}

	@PutMapping()
	public ResponseEntity<String> updateRestaurant(Restaurant restaurant) {
		restaurantService.updateRestaurantEntry(restaurant);
		return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant Updated Successfully");
	}

	@GetMapping()
	public ResponseEntity<List<Restaurant>> getAllRestaurants() {
		return ResponseEntity.status(HttpStatus.FOUND).body(restaurantService.findAllRestaurants());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> getRestaurantById(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.FOUND).body(restaurantService.findByRestaurantId(id));
	}

	@GetMapping("/{name}")
	public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable("name") String name){
		return ResponseEntity.status(HttpStatus.FOUND).body(restaurantService.findByRestaurantName(name));
	}

	@GetMapping("/{ownerId}")
	public ResponseEntity<List<Restaurant>> getRestaurantsByOwnerId(@PathVariable("ownerId") String ownerId){
		return ResponseEntity.status(HttpStatus.FOUND).body(restaurantService.findByOwnerId(ownerId));
	}

	@GetMapping("/{managerId}")
	public ResponseEntity<List<Restaurant>> getRestaurantsByManagerId(@PathVariable("managerId") String managerId){
		return ResponseEntity.status(HttpStatus.FOUND).body(restaurantService.findByManagerId(managerId));
	}

	@GetMapping("/{staffId}")
	public ResponseEntity<List<Restaurant>> getRestaurantsByStaffId(@PathVariable("staffId") String staffId){
		return ResponseEntity.status(HttpStatus.FOUND).body(restaurantService.findByStaffId(staffId));
	}
}
