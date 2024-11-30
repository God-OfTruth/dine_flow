package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.models.Tax;
import com.ritesh.dineflow.services.TaxService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tax Controller", description = "Will handle all the Tax related to the Application use case")
@RestController
@RequestMapping("/api/taxes")
public class TaxController {

	@Autowired
	private TaxService taxService;

	@PostMapping()
	public ResponseEntity<Void> createTaxEntry(@RequestBody Tax tax) {
		taxService.createTaxEntry(tax);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping()
	public ResponseEntity<List<Tax>> getAllTaxEntries() {
		return ResponseEntity.status(HttpStatus.FOUND).body(taxService.getAllTaxEntries());
	}

	@PutMapping()
	public ResponseEntity<Void> updateTaxEntry(@RequestBody Tax tax) {
		taxService.updateTaxEntry(tax);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tax> getTaxEntryById(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.FOUND).body(taxService.getTaxEntryById(id));
	}

}
