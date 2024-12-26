package com.ritesh.dineflow.repositories;

import com.ritesh.dineflow.enums.TransactionMethodType;
import com.ritesh.dineflow.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
	public List<Transaction> findByUserMobileNumber(String userId);

	public List<Transaction> findByMethodType(TransactionMethodType type);

	public List<Transaction> findByUserId(String userId);

	public List<Transaction> findByRestaurantId(String id);

	public List<Transaction> findByRestaurantIdIn(List<String> id);
}
