package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.dto.TransactionFilterDTO;
import com.ritesh.dineflow.models.Transaction;
import com.ritesh.dineflow.services.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Transaction Controller")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/filter")
	public ResponseEntity<List<Transaction>> getAllTransactions(@RequestBody TransactionFilterDTO filterDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAllTransactions(filterDTO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByTransactionId(id));
	}

	@GetMapping("/restaurant/{id}")
	public ResponseEntity<List<Transaction>> getTransactionByRestaurantId(@PathVariable("id") String id) {
		return ResponseEntity.status(HttpStatus.OK).body(transactionService.findByRestaurantId(id));
	}

	@PostMapping()
	public ResponseEntity<Void> saveTransaction(@RequestBody Transaction transaction) {
		transactionService.createTransactionEntry(transaction);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
