package com.ritesh.dineflow.models;

import com.ritesh.dineflow.enums.PriceType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tax {

	private String name;

	private double value;

	private PriceType valueType;

}
