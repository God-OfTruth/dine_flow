package com.ritesh.dineflow.repositories.treeHandler;

import com.ritesh.dineflow.models.treeHandler.Story;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StoryRepository extends MongoRepository<Story, String> {

	List<Story> findByOwner(String id);

}
