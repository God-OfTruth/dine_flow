package com.ritesh.dineflow.services;

import com.ritesh.dineflow.configuration.security.JwtTokenProvider;
import com.ritesh.dineflow.dto.AuthenticationRequest;
import com.ritesh.dineflow.dto.RegistrationRequest;
import com.ritesh.dineflow.enums.UserRole;
import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
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

	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	public void register(RegistrationRequest request) {

		Role role = roleRepository.findByRole(UserRole.USER).orElseThrow(() -> new UnauthorizedAccessException("Unable To Create Account"));
		UserProfile profile = userProfileService.createUserProfile(UserProfile.builder().build());

		User user = User.builder().email(request.getEmail())
				.password(PasswordUtils.encodePassword(request.getPassword()))
				.username(request.getUsername())
				.profileId(profile.getId())
				.roles(Set.of(role))
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
}
