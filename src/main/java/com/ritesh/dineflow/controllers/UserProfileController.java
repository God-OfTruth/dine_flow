package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.models.UserProfile;
import com.ritesh.dineflow.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

	@Autowired
	private UserProfileService userProfileService;

	@GetMapping("/{id}")
	public ResponseEntity<UserProfile> getProfile(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.OK).body(userProfileService.getById(id));
	}
}
