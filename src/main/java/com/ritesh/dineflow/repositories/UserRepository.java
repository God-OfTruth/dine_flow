package com.ritesh.dineflow.repositories;


import com.ritesh.dineflow.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByEmail(String email);

	List<User> findByCreatedBy(String id);

	Optional<User> findByOauthIdAndOauthProvider(String oauthId, String oauthProvider);

	Optional<User> findByUsername(String username);
}
