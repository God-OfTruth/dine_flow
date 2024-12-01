package com.ritesh.dineflow.repositories;

import com.ritesh.dineflow.models.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

	Optional<Restaurant> findByName(String name);

	List<Restaurant> findByOwnerId(String ownerId);

	List<Restaurant> findByManagersIn(String managerId);

	List<Restaurant> findByStaffsIn(String staffId);

}
