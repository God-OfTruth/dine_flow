package com.ritesh.dineflow.services;

import com.ritesh.dineflow.dto.EmailInfo;
import com.ritesh.dineflow.enums.UserRole;
import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.exceptions.UserNotFoundException;
import com.ritesh.dineflow.models.Role;
import com.ritesh.dineflow.models.User;
import com.ritesh.dineflow.models.UserProfile;
import com.ritesh.dineflow.repositories.RoleRepository;
import com.ritesh.dineflow.repositories.UserRepository;
import com.ritesh.dineflow.utils.PasswordUtils;
import com.ritesh.dineflow.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private EmailService emailService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	public User findUserById(String id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
	}

	public List<User> findAllUsers() {
		if (SecurityUtils.isCurrentUserInRole("ROLE_SUPER_ADMIN")) {
			return userRepository.findAll();
		}
		return userRepository.findByCreatedBy(SecurityUtils.getCurrentUserId());
	}

	public User saveUser(User user) {
		User previousUser = userRepository.findByEmail(user.getEmail())
				.orElse(userRepository.findByUsername(user.getUsername()).orElse(null));
		if (previousUser == null) {
			if (user.getProfileId() == null) {
				UserProfile userProfile = userProfileService.createUserProfile(UserProfile.builder().build());
				user.setProfileId(userProfile.getId());
			}
			return userRepository.save(user);
		}
		throw new ResourceAlreadyPresentException("User Already Present");
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> findByOauthIdAndOauthProvider(String oauthId, String oauthProvider) {
		return userRepository.findByOauthIdAndOauthProvider(oauthId, oauthProvider);
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void updateUser(User user) {
		if (user.getId() == null) {
			throw new ResourceNotFoundException("Account Not Fount");
		}

		Optional<User> previousUser = userRepository.findById(user.getId());
		if (previousUser.isPresent()) {
			userRepository.save(user);
			return;
		}
		throw new ResourceNotFoundException("Account Not Fount");
	}

	public void deleteUser(String id) {
		if (userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
		} else {
			throw new UserNotFoundException("User not Found");
		}
	}

	public User getCurrentUser() {
		return SecurityUtils.getCurrentUser();
	}

	public List<User> findAllByIds(List<String> ids) {
		return userRepository.findAllById(ids);
	}

	public User createRestaurantManager(String email) {
		User user = userRepository.findByEmail(email).orElse(null);
		if (user == null) {
			UserProfile userProfile = userProfileService.createUserProfile(UserProfile.builder()
					.restaurantsLicensed(0)
					.build());
			String password = PasswordUtils.generateRandomPassword(5);
			String encodedPassword = PasswordUtils.encodePassword(password);
			LOGGER.info("Password for Manager {} is {}", email, password);
			Role role = roleRepository.findByRole(UserRole.MANAGER)
					.orElseThrow(() -> new ResourceNotFoundException("Try Again")); // TODO: Send Mail to SuperAdmin
			sendEmail(email, email, password, "Manager");
			user = User.builder()
					.email(email)
					.username(email)
					.password(encodedPassword)
					.roles(Set.of(role))
					.isExternalAccount(false)
					.profileId(userProfile.getId())
					.build();
			return userRepository.save(user);
		}
		throw new ResourceAlreadyPresentException("User Already Present");
	}

	public User createRestaurantStaff(String email) {
		User user = userRepository.findByEmail(email).orElse(null);
		if (user == null) {
			UserProfile userProfile = userProfileService.createUserProfile(UserProfile.builder()
					.restaurantsLicensed(0)
					.build());
			String password = PasswordUtils.generateRandomPassword(5);
			String encodedPassword = PasswordUtils.encodePassword(password);
			LOGGER.info("Password for Staff {} is {}", email, password);
			Role role = roleRepository.findByRole(UserRole.STAFF)
					.orElseThrow(() -> new ResourceNotFoundException("Try Again")); // TODO: Send Mail to SuperAdmin
			sendEmail(email, email, password, "Staff");
			user = User.builder()
					.email(email)
					.username(email)
					.password(encodedPassword)
					.roles(Set.of(role))
					.isExternalAccount(false)
					.profileId(userProfile.getId())
					.build();
			return userRepository.save(user);
		}
		throw new ResourceAlreadyPresentException("User Already Present");
	}

	public User createRestaurantOwner(User user) {
		User previousUser = userRepository.findByEmail(user.getEmail())
				.orElse(userRepository.findByUsername(user.getUsername()).orElse(null));
		if (previousUser == null) {
			UserProfile userProfile = userProfileService.createUserProfile(UserProfile.builder().restaurantsLicensed(1).build());
			Role role = roleRepository.findByRole(UserRole.ADMIN)
					.orElseThrow(() -> new ResourceNotFoundException("Try Again")); // TODO: Send Mail to SuperAdmin
			String password = PasswordUtils.generateRandomPassword(5);
			String encodedPassword = PasswordUtils.encodePassword(password);

			sendEmail(user.getEmail(), user.getUsername(), password, "Owner");

			User owner = User.builder()
					.email(user.getEmail())
					.username(user.getUsername())
					.password(encodedPassword)
					.isExternalAccount(false)
					.profileId(userProfile.getId())
					.roles(Set.of(role))
					.mobileNumber(user.getMobileNumber())
					.enabled(false)
					.build();

			return userRepository.save(owner);
		}
		throw new ResourceAlreadyPresentException("User with same email or username exists");
	}

	public void updateStatusOfTenant(String userId, boolean status) {
		User user = findUserById(userId);
		if (user.getEnabled().equals(status)) {
			return;
		}
		user.setEnabled(status);
		userRepository.save(user);
	}

	private void sendEmail(String email, String username, String password, String authority) {
		Context context = new Context();
		context.setVariable("username", username);
		context.setVariable("password", password);
		context.setVariable("authority", authority);
		context.setVariable("uriPath", "/sign-in");

		emailService.sendMail(EmailInfo.builder()
				.to(List.of(email))
				.from("no-reply.dineflow.com")
				.html(true)
				.templateName("new-account")
				.subject("Welcome to Dine Flow")
				.context(context)
				.build(), null);
	}
}