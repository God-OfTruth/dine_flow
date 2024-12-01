package com.ritesh.dineflow.services;

import com.ritesh.dineflow.enums.Tag;
import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.models.Item;
import com.ritesh.dineflow.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	public void createItemEntry(Item item) {
		if (item.getId() == null) {
			itemRepository.save(item);
			return;
		}
		throw new ResourceAlreadyPresentException("Item Already Present");
	}

	public void updateItemEntry(Item item) {
		if (item.getId() == null) {
			throw new ResourceNotFoundException("Item Not Present");
		}
		itemRepository.save(item);
	}

	public Item getItemById(String id) {
		return itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item Not Present"));
	}

	public List<Item> getItemsByTag(Tag tag){
		return itemRepository.findByTags(tag);
	}

	public List<Item> getItemsByTags(List<Tag> tags){
		return itemRepository.findByTagsIn(tags);
	}

	public List<Item> getAllItems(){
		return itemRepository.findAll();
	}
}
