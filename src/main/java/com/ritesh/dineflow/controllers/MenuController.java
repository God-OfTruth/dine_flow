package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.models.Menu;
import com.ritesh.dineflow.services.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Menu Controller")
@RestController
@RequestMapping("/api/menus")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@PostMapping()
	public ResponseEntity<Void> addMenu(@RequestBody Menu menu) {
		menuService.createMenuEntry(menu);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping()
	public ResponseEntity<Void> updateMenuEntry(@RequestBody Menu menu) {
		menuService.updateMenuEntry(menu);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Menu> getMenuById(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.OK).body(menuService.getMenuById(id));
	}

	@GetMapping()
	public ResponseEntity<List<Menu>> getAllMenus() {
		return ResponseEntity.status(HttpStatus.OK).body(menuService.getAllMenus());
	}
}
