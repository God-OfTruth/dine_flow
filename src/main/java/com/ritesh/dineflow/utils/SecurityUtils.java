package com.ritesh.dineflow.utils;

import com.ritesh.dineflow.enums.UserPermission;
import com.ritesh.dineflow.exceptions.UnauthorizedAccessException;
import com.ritesh.dineflow.models.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

	private SecurityUtils() {
	}

	/**
	 * Get the login of the current user.
	 *
	 * @return the login of the current user
	 */
	public static User getCurrentUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		User userDetails = null;
		if (authentication != null && authentication.isAuthenticated()) {
			if (authentication.getPrincipal() instanceof User) {
				userDetails = (User) authentication.getPrincipal();
			}
		}
		return userDetails;
	}

	public static String getCurrentUserId() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		String userId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			if (authentication.getPrincipal() instanceof User springSecurityUser) {
				userId = springSecurityUser.getId();
			}
		}
		return userId;
	}

	public static void checkUserIdOrAdmin(String userId) {
		if (!Objects.equals(getCurrentUserId(), userId)
				&& !SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
			throw new UnauthorizedAccessException("Unauthorized Access Blocked");
		}
	}

	public static void checkUserId(String userId) {
		if (!Objects.equals(getCurrentUserId(), userId)) {
			throw new UnauthorizedAccessException("Unauthorized Access Blocked");
		}
	}

	public static String getCurrentUserMobileNumber() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		String userPhone = null;
		if (authentication != null && authentication.isAuthenticated()) {
			if (authentication.getPrincipal() instanceof User springSecurityUser) {
				userPhone = springSecurityUser.getMobileNumber();
			}
		}
		return userPhone;
	}

	public static String getCurrentUserEmail() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		String userEmail = null;
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof User springSecurityUser) {
				userEmail = springSecurityUser.getEmail();
			}
		}
		return userEmail;
	}

	/**
	 * Check if a user is authenticated.
	 *
	 * @return true if the user is authenticated, false otherwise
	 */
	public static boolean isAuthenticated() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		return securityContext.getAuthentication().isAuthenticated();
	}

	/**
	 * If the current user has a specific authority (security role).
	 *
	 * <p>The name of this method comes from the isUserInRole() method in the Servlet API
	 *
	 * @param authority the authority to check
	 * @return true if the current user has the authority, false otherwise
	 */
	public static boolean isCurrentUserInRole(String authority) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			return authentication.getAuthorities().contains(new SimpleGrantedAuthority(authority));
		}
		return false;
	}

	public static void isCurrentUserInRoleThrow(String authority) {
		boolean isInRole = false;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			isInRole = authentication.getAuthorities().contains(new SimpleGrantedAuthority(authority));
		}
		if (!isInRole) {
			throw new AccessDeniedException("Access Denied");
		}
	}

	public static boolean isCurrentUserContainAnyPermission(List<UserPermission> permissions) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();

		if (authentication != null) {
			return permissions.stream().anyMatch(per ->
					authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + per))
			);
		}
		return false;
	}

	public static boolean isCurrentUserInRole(RoleHierarchy roleHeir, String authority) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			Collection<? extends GrantedAuthority> allAuthorities =
					roleHeir.getReachableGrantedAuthorities(authentication.getAuthorities());
			return allAuthorities.contains(new SimpleGrantedAuthority(authority));
		}
		return false;
	}
}
