package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.models.Menu;
import com.ritesh.dineflow.services.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Menu Controller")
@RestController
@RequestMapping("/api/menus")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@PostMapping("/{restaurantId}")
	public ResponseEntity<String> addMenuEntry(@PathVariable("restaurantId") String restaurantId,
			@RequestBody Menu menu) {
		menuService.createMenuEntry(menu, restaurantId);
		return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created Menu");
	}

	@PutMapping()
	public ResponseEntity<String> updateMenuEntry(@RequestBody Menu menu) {
		menuService.updateMenuEntry(menu);
		return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Updated Menu");
	}

	@GetMapping("/{id}")
	public ResponseEntity<Menu> getMenuById(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.OK).body(menuService.getMenuById(id));
	}
}
