package com.ritesh.dineflow.services;

import com.ritesh.dineflow.exceptions.InvalidRequestException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.models.UserProfile;
import com.ritesh.dineflow.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
	@Autowired
	private UserProfileRepository userProfileRepository;


	public UserProfile getById(String id) {
		return userProfileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Profile Not Present"));
	}

	public UserProfile updateProfile(UserProfile profile) {
		if (profile.getId() != null) {
			return userProfileRepository.save(profile);
		}
		throw new ResourceNotFoundException("No Profile Exist");
	}

	public UserProfile createUserProfile(UserProfile profile) {
		return userProfileRepository.save(profile);
	}

	@PreAuthorize(("hasRole('ROLE_SUPER_ADMIN')"))
	public void updateRestaurantLicensed(String userProfileId, int count) {
		UserProfile userProfile = userProfileRepository.findById(userProfileId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		if(count <= 0){
			throw new InvalidRequestException("Invalid Property");
		}
		userProfile.setRestaurantsLicensed(count);
		userProfileRepository.save(userProfile);
	}
}
