package com.ritesh.dineflow.configuration.seeders;

import com.ritesh.dineflow.enums.UserRole;
import com.ritesh.dineflow.models.User;
import com.ritesh.dineflow.models.UserProfile;
import com.ritesh.dineflow.repositories.RoleRepository;
import com.ritesh.dineflow.services.UserProfileService;
import com.ritesh.dineflow.services.UserService;
import com.ritesh.dineflow.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
@Order(2)
public class UserSeeder implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserProfileService userProfileService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.generateUsers();
	}

	private void generateUsers() {

		List<User> users = List.of(
				User.builder()
						.email("dineflow@gmail.com")
						.username("dine_flow")
						.password(PasswordUtils.encodePassword("DineFlow@123"))
						.enabled(true)
						.roles(Set.of(Objects.requireNonNull(roleRepository.findByRole(UserRole.SUPER_ADMIN).orElse(null))))
						.build()
		);

		users.forEach(user -> {
			Optional<User> userOptional = userService.findByUsername(user.getUsername());
			userOptional.ifPresentOrElse(System.out::println, () -> {
				UserProfile userProfile = userProfileService.createUserProfile(UserProfile.builder()
						.firstName("Dine")
						.lastName("Flow")
						.build());
				user.setProfileId(userProfile.getId());
				userService.saveUser(user);
			});
		});

	}
}
