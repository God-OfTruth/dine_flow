package com.ritesh.dineflow.repositories;

import com.ritesh.dineflow.enums.UserPermission;
import com.ritesh.dineflow.models.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PermissionRepository extends MongoRepository<Permission, String> {
	Optional<Permission> findByPermission(UserPermission permission);
}
