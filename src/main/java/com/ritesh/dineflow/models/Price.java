package com.ritesh.dineflow.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Price {
	private double amount;
	private double discount;
}
