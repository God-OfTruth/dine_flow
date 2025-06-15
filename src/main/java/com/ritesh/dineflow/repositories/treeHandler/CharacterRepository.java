package com.ritesh.dineflow.repositories.treeHandler;

import com.ritesh.dineflow.models.treeHandler.Character;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepository extends MongoRepository<Character, String> {
}
