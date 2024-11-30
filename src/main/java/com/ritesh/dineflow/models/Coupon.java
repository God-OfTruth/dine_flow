package com.ritesh.dineflow.models;

import com.ritesh.dineflow.enums.CouponApplicableType;
import com.ritesh.dineflow.enums.PriceType;
import com.ritesh.dineflow.enums.TaxType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "coupons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {
	@Id
	private String id;

	private String couponCode;

	private String title;

	private String description;

	private PriceType discountType;

	private long value;

	private double validFrom;

	private double validTill;

	private CouponApplicableType applicableType;

	@Builder.Default
	private Map<TaxType, Tax> taxes = new HashMap<>();

	@Builder.Default
	private boolean isPublic = false;

	@Builder.Default
	private Map<String, String> meta = new HashMap<>();

}
