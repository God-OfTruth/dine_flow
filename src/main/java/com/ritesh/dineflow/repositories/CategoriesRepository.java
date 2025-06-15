package com.ritesh.dineflow.repositories;

import com.ritesh.dineflow.models.Categories;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriesRepository extends MongoRepository<Categories, String> {
	Optional<Categories> findByName(String name);

	List<Categories> findByCreatedBy(String id);

	List<Categories> findByRestaurantIdsIn(String id);

}
