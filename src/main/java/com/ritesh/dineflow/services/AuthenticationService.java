package com.ritesh.dineflow.services;

import com.ritesh.dineflow.configuration.security.JwtTokenProvider;
import com.ritesh.dineflow.dto.*;
import com.ritesh.dineflow.enums.UserRole;
import com.ritesh.dineflow.exceptions.InvalidRequestException;
import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.exceptions.UnauthorizedAccessException;
import com.ritesh.dineflow.models.Role;
import com.ritesh.dineflow.models.User;
import com.ritesh.dineflow.models.UserProfile;
import com.ritesh.dineflow.repositories.RoleRepository;
import com.ritesh.dineflow.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Set;

@Service
public class AuthenticationService {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Autowired
	private EmailService emailService;

	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	public void register(RegistrationRequest request) {
		Role role = roleRepository.findByRole(UserRole.USER).orElseThrow(() -> new UnauthorizedAccessException("Unable To Create Account"));
		UserProfile profile = userProfileService.createUserProfile(UserProfile.builder().build());

		User user = User.builder().email(request.getEmail())
				.password(PasswordUtils.encodePassword(request.getPassword()))
				.username(request.getUsername())
				.profileId(profile.getId())
				.roles(Set.of(role))
				.enabled(false)
				.isExternalAccount(false)
				.build();
		User previousUser = userService.findByEmail(request.getEmail()).orElse(userService.findByUsername(request.getUsername()).orElse(null));
		if (previousUser == null) {
			userService.saveUser(user);
		} else {
			logger.info("User Already Present");
			throw new ResourceAlreadyPresentException("User Already Present");
		}
	}

	public String authenticate(AuthenticationRequest request) {
		Authentication authentication = authenticationProvider.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		return tokenProvider.generateToken(authentication, null);
	}

	public void forgotPassword(ForgotPassword forgotPassword) {
		User user = userService.findByEmail(forgotPassword.getEmail()).orElse(userService.findByUsername(forgotPassword.getUsername()).orElse(null));
		if (user == null) {
			throw new ResourceNotFoundException("User Not Present");
		}
		String password = PasswordUtils.generateRandomPassword(5);
		String encodedPassword = PasswordUtils.encodePassword(password);
		user.setEnabled(false);
		user.setPassword(encodedPassword);

		userService.updateUser(user);
		sendEmail(user.getEmail(), user.getUsername(), password);
	}

	private void sendEmail(String email, String username, String password) {
		Context context = new Context();
		context.setVariable("username", username);
		context.setVariable("password", password);
		context.setVariable("email", email);

		emailService.sendMail(EmailInfo.builder()
				.to(List.of(email))
				.from("no-reply.dineflow.com")
				.html(true)
				.templateName("reset-password")
				.subject("Welcome to Dine Flow")
				.context(context)
				.build(), null);
	}

	public void resetPassword(ResetPassword resetPassword) {
		// Find the user by email or username
		User user = userService.findByEmail(resetPassword.getEmail())
				.or(() -> userService.findByUsername(resetPassword.getUsername()))
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		// Verify the old password
		if (!PasswordUtils.matchPassword(resetPassword.getOldPassword(), user.getPassword())) {
			throw new UnauthorizedAccessException("Invalid credentials");
		}

		// Ensure the new password is different from the old one
		if (resetPassword.getNewPassword().equals(resetPassword.getOldPassword())) {
			throw new InvalidRequestException("New password cannot be the same as the old password");
		}

		// Update the user's password
		user.setPassword(PasswordUtils.encodePassword(resetPassword.getNewPassword()));
		user.setEnabled(true); // Enable the user if necessary (validate this logic)

		// Persist the changes
		userService.updateUser(user);
	}

}
