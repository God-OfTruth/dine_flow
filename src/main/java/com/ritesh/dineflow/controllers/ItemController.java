package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.models.Item;
import com.ritesh.dineflow.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ItemController {

	@Autowired
	private ItemService itemService;

	@PostMapping()
	public ResponseEntity<String> createItem(@RequestBody Item item){
		itemService.createItemEntry(item);
		return ResponseEntity.ok().body("Item Created Successfully");
	}

	@PutMapping()
	public ResponseEntity<String> updateItem(@RequestBody Item item){
		itemService.updateItemEntry(item);
		return ResponseEntity.ok().body("Item Updated Successfully");
	}

	@GetMapping()
	public ResponseEntity<List<Item>> getAllItems(){
		return ResponseEntity.status(HttpStatus.FOUND).body(itemService.getAllItems());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemsById(@PathVariable("id") String id){
		return ResponseEntity.status(HttpStatus.FOUND).body(itemService.getItemById(id));
	}
}
