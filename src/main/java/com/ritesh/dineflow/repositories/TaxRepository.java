package com.ritesh.dineflow.repositories;

import com.ritesh.dineflow.enums.TaxType;
import com.ritesh.dineflow.models.Tax;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaxRepository extends MongoRepository<Tax, String> {

	List<Tax> findByType(TaxType type);

	Optional<Tax> findByName(String name);
}
