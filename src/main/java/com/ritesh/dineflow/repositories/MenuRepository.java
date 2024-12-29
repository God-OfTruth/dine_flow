package com.ritesh.dineflow.repositories;

import com.ritesh.dineflow.models.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends MongoRepository<Menu, String> {
	Optional<Menu> findByName(String name);

	List<Menu> findByCreatedBy(String id);

	List<Menu> findByRestaurantIdsIn(String id);

}
