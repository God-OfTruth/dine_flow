package com.ritesh.dineflow.repositories;

import com.ritesh.dineflow.enums.Tag;
import com.ritesh.dineflow.models.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, String> {

	Optional<Item> findByName(String name);

	List<Item> findByTagsIn(List<Tag> tags);

	List<Item> findByTags(Tag tag);

}
