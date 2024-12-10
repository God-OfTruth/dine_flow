package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.models.User;
import com.ritesh.dineflow.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Controller")
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/account")
	public ResponseEntity<User> getCurrentAccount() {
		return ResponseEntity.ok(userService.getCurrentUser());
	}

	@GetMapping()
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUsers());
	}

	@PostMapping("/activate/{id}/{status}")
	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
	public ResponseEntity<Void> updateUserStatus(@PathVariable("status") boolean status, @PathVariable("id") String userId) {
		userService.updateStatusOfTenant(userId, status);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping()
	@PreAuthorize("hasAuthority('CREATE_ACCOUNT')")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = userService.saveUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	@PostMapping("/owner")
	@PreAuthorize(("hasRole('ROLE_SUPER_ADMIN')"))
	public ResponseEntity<User> createOwnerAccount(@RequestBody User user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createRestaurantOwner(user));
	}
}
