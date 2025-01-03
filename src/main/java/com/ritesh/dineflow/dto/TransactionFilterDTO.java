package com.ritesh.dineflow.dto;

import com.ritesh.dineflow.enums.TransactionMethodType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionFilterDTO {
	@NonNull
	private Long startTime;
	@NonNull
	private Long endTime;
	private List<TransactionMethodType> methodType;
	private List<String> restaurants;
}
