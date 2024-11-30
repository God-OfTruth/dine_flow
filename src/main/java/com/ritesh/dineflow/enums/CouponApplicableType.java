package com.ritesh.dineflow.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CouponApplicableType {
	ALL,
	FIRST_TIME,
	SPECIFIC
}
