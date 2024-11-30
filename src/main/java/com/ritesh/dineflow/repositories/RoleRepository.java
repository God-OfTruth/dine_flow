package com.ritesh.dineflow.repositories;

import com.ritesh.dineflow.enums.UserRole;
import com.ritesh.dineflow.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
	Optional<Role> findByRole(UserRole role);
}
