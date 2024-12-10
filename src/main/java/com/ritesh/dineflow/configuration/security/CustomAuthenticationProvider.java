package com.ritesh.dineflow.configuration.security;

import com.ritesh.dineflow.exceptions.UserNotFoundException;
import com.ritesh.dineflow.services.UserService;
import com.ritesh.dineflow.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		// Load user from database
		UserDetails userDetails = userService.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("No User Found"));

		// Verify password
		if (!PasswordUtils.matchPassword(password, userDetails.getPassword())) {
			logger.info("Invalid Credentials");
			throw new AuthenticationException("Invalid credentials") {
			};
		}
		if (!userDetails.isEnabled()) {
			logger.info("Disabled User");
			throw new AuthenticationException("User is Disabled") {
			};
		} else {
			return new UsernamePasswordAuthenticationToken(userDetails, password);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
