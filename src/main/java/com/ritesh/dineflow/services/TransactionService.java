package com.ritesh.dineflow.services;

import com.ritesh.dineflow.dto.TransactionFilterDTO;
import com.ritesh.dineflow.enums.TransactionMethodType;
import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.models.Restaurant;
import com.ritesh.dineflow.models.Transaction;
import com.ritesh.dineflow.models.User;
import com.ritesh.dineflow.repositories.TransactionRepository;
import com.ritesh.dineflow.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class TransactionService {

	@Value("${spring.application.timezone:Asia/Kolkata}")
	private String timezone;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private MongoTemplate mongoTemplate;

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

	public List<Transaction> findAllTransactions(TransactionFilterDTO filterDTO) {
		Query query = new Query();
		if (filterDTO.getMethodType() != null && !filterDTO.getMethodType().isEmpty()) {
			query.addCriteria(Criteria.where("methodType").in(filterDTO.getMethodType()));
		}
		if (filterDTO.getRestaurants() != null && !filterDTO.getRestaurants().isEmpty()) {
			query.addCriteria(Criteria.where("restaurantId").in(filterDTO.getRestaurants()));
		}
		LocalDateTime startTime = LocalDateTime.ofInstant(
				Instant.ofEpochMilli(filterDTO.getStartTime()),
				ZoneId.of(timezone)
		);
		LocalDateTime endTime = LocalDateTime.ofInstant(
				Instant.ofEpochMilli(filterDTO.getEndTime()),
				ZoneId.of(timezone)
		);
		query.addCriteria(Criteria.where("created_date").gte(startTime).lte(endTime));
		return mongoTemplate.find(query, Transaction.class);
	}

	public List<Transaction> findByRestaurantId(String restaurantId) {
		return transactionRepository.findByRestaurantId(restaurantId);
	}
}
