package com.ritesh.dineflow.services;

import com.ritesh.dineflow.enums.TransactionMethodType;
import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.models.Restaurant;
import com.ritesh.dineflow.models.Transaction;
import com.ritesh.dineflow.models.User;
import com.ritesh.dineflow.repositories.TransactionRepository;
import com.ritesh.dineflow.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private RestaurantService restaurantService;

	public void createTransactionEntry(Transaction transaction) {
		if (transaction.getId() != null) {
			throw new ResourceAlreadyPresentException("Cannot Update previous Transaction");
		}
		String currentUserId = SecurityUtils.getCurrentUserId();
		transaction.setUserId(currentUserId);
		transactionRepository.save(transaction);
	}

	public Transaction findByTransactionId(String id) {
		return transactionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Transaction Found"));
	}

	public List<Transaction> findByCustomerMobileNumber(String number) {
		return transactionRepository.findByUserMobileNumber(number);
	}

	public List<Transaction> findByMethodUsed(TransactionMethodType type) {
		return transactionRepository.findByMethodType(type);
	}

	public List<Transaction> findAllTransactions() {
		User currentUser = SecurityUtils.getCurrentUser();
		if (SecurityUtils.isCurrentUserInRole("ROLE_SUPER_ADMIN")) {
			return transactionRepository.findAll();
		}
		if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
			List<String> restaurants = restaurantService.findByOwnerId(currentUser.getId()).stream()
					.map((Restaurant::getId)).toList();
			return transactionRepository.findByRestaurantIdIn(restaurants);
		}
		return transactionRepository.findByUserId(currentUser.getId());
	}

	public List<Transaction> findByRestaurantId(String restaurantId){
		return transactionRepository.findByRestaurantId(restaurantId);
	}
}
