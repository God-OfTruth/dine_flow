package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.models.Item;
import com.ritesh.dineflow.services.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Menu Items Controller")
@RestController()
@RequestMapping("/api/items")
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

	@GetMapping("/{id}")
	public ResponseEntity<Item> getAllItems(@PathVariable("id") String id){
		return ResponseEntity.status(HttpStatus.FOUND).body(itemService.getItemById(id));
	}
}
