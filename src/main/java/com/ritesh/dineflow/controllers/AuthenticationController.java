package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.dto.*;
import com.ritesh.dineflow.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication Controller")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	// Endpoint for user registration
	@Operation(summary = "Register A New User", description = "Registers a new User")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successfully Created the User"),
			@ApiResponse(responseCode = "409", description = "User with same Email or Username Present")
	})
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
		authenticationService.register(request);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	// Endpoint for user login
	@Operation(summary = "Login", description = "Used to Get Access Token for Registered User")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Returns Access Token for Authorization"),
			@ApiResponse(responseCode = "404", description = "User with Username is Not Registered Yet"),
			@ApiResponse(responseCode = "401", description = "Username and/or Password Not Correct")
	})

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
		String token = authenticationService.authenticate(request);
		AuthenticationResponse response = new AuthenticationResponse();
		response.setAccessToken(token);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPassword password){
		authenticationService.forgotPassword(password);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/reset-password")
	public ResponseEntity<Void> resetPassword(@RequestBody ResetPassword resetPassword){
		authenticationService.resetPassword(resetPassword);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
