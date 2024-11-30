package com.ritesh.dineflow.services;

import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.exceptions.UserNotFoundException;
import com.ritesh.dineflow.models.User;
import com.ritesh.dineflow.models.UserProfile;
import com.ritesh.dineflow.repositories.UserRepository;
import com.ritesh.dineflow.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserProfileService userProfileService;

	public User findUserById(String id){
		return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not Found"));
	}

	public User saveUser(User user) {
		User previousUser = userRepository.findByEmail(user.getEmail()).orElse(userRepository.findByUsername(user.getUsername()).orElse(null));
		if (previousUser == null) {
			if(user.getProfileId() == null){
				UserProfile userProfile = userProfileService.updateProfile(UserProfile.builder().build());
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

	public User updateUser(User user) {
		if (user.getId() == null) {
			throw new ResourceNotFoundException("Account Not Fount");
		}

		Optional<User> previousUser = userRepository.findById(user.getId());
		if (previousUser.isPresent()) {
			return userRepository.save(user);
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

	public List<User> findAllByIds(List<String> ids){
		return userRepository.findAllById(ids);
	}
}