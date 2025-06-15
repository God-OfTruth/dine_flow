package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.models.Categories;
import com.ritesh.dineflow.services.CategoriesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Menu Controller")
@RestController
@RequestMapping("/api/menus")
public class CategoriesController {

	@Autowired
	private CategoriesService menuService;

	@PostMapping()
	public ResponseEntity<Void> addMenu(@RequestBody Categories menu) {
		menuService.createMenuEntry(menu);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping()
	public ResponseEntity<Void> updateMenuEntry(@RequestBody Categories menu) {
		menuService.updateMenuEntry(menu);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categories> getMenuById(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.OK).body(menuService.getMenuById(id));
	}

	@GetMapping()
	public ResponseEntity<List<Categories>> getAllMenus() {
		return ResponseEntity.status(HttpStatus.OK).body(menuService.getAllMenus());
	}
}
