package com.ritesh.dineflow.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionItem {
	private String id;
	private String itemName;
	private String option;
	private int quantity;
	private long cost;
}
