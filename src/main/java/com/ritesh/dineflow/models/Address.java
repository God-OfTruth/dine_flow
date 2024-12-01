package com.ritesh.dineflow.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	private String shortAddress;

	private String locality;

	private String plusCode;

	private String fullAddress;

	private Map<String, String> mapsUrls;
}
