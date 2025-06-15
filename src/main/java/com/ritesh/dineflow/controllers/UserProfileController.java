package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.models.UserProfile;
import com.ritesh.dineflow.services.UserProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Profile Controller")
@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

	@Autowired
	private UserProfileService userProfileService;

	@GetMapping("/{id}")
	public ResponseEntity<UserProfile> getProfile(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.OK).body(userProfileService.getById(id));
	}

	@PostMapping("update-licensed-restaurant/{id}/{count}")
	public ResponseEntity<Void> updateRestaurantCount(@PathVariable("id") String userProfileId, @PathVariable("count") int count) {
		userProfileService.updateRestaurantLicensed(userProfileId, count);
		return ResponseEntity.ok().build();
	}
}
