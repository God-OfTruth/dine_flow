package com.ritesh.dineflow.repositories;

import com.ritesh.dineflow.models.Tax;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TaxRepository extends MongoRepository<Tax, String> {

	Optional<Tax> findByName(String name);
}
