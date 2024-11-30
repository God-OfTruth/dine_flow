package com.ritesh.dineflow.repositories;

import com.ritesh.dineflow.models.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
}
