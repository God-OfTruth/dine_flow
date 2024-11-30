package com.ritesh.dineflow.configuration.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.ritesh.dineflow.repositories")
public class MongoConfig extends AbstractMongoClientConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(MongoConfig.class);

	@Value("${spring.mongo.database.name}")
	private String dbName;

	@Value("${spring.mongo.database.url}")
	private String connectionString;

	@Override
	@NonNull
	protected String getDatabaseName() {
		return dbName;
	}

	@Override
	@NonNull
	public MongoClient mongoClient() {
		try {
			LOGGER.info("Initializing MongoDB connection with connection string: {}", connectionString);
			ConnectionString string = new ConnectionString(connectionString);
			MongoClientSettings clientSettings = MongoClientSettings.builder()
					.applyConnectionString(string)
					.build();
			return MongoClients.create(clientSettings);
		} catch (MongoException ex) {
			LOGGER.error("Error in connecting to MongoDB: {}", ex.getMessage(), ex);
			throw new RuntimeException("Failed to create MongoClient", ex); // Throw a runtime exception instead of returning null
		}
	}

	@Override
	protected boolean autoIndexCreation() {
		return true; // Enable automatic index creation if required
	}
}
