package com.ritesh.dineflow.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ritesh.dineflow.enums.TransactionMethodType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "transactions")
public class Transaction extends AbstractAuditingEntity {

	@Id
	private String id;

	@NonNull
	private TransactionMethodType methodType;

	@NonNull
	private List<Item> items;

	@NonNull
	private Price finalPrice;

	private String userMobileNumber;

	@JsonIgnore
	private String userId;

	private String restaurantId;

	private String comment;

	@Builder.Default
	private List<String> tags = new ArrayList<>();

	private String couponCode;

	private String couponId;

	@Builder.Default
	private Map<String, String> meta = new HashMap<>();

}
