package com.ritesh.dineflow.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionMethodType {
	CASH,
	UPI,
	CARD
}
