package com.ritesh.dineflow.repositories;

import com.ritesh.dineflow.models.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

	Optional<RefreshToken> findByToken(String token);

	void deleteByUserName(String username);
}
