package com.ritesh.dineflow.configuration.database;

import com.ritesh.dineflow.models.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
	@Override
	@NonNull
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty(); // Return empty if no authenticated user
		}
		if (authentication.getPrincipal().equals("anonymousUser")) {
			return Optional.empty(); // Or any other user detail (e.g., ID)
		}
		User user = (User) authentication.getPrincipal();
		return Optional.of(user.getId()); // Or any other user detail (e.g., ID)
	}
}
