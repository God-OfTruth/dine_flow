package com.ritesh.dineflow.models;

import com.ritesh.dineflow.enums.PriceType;
import com.ritesh.dineflow.enums.TaxType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "taxes")
public class Tax {

	@Id
	private String id;

	private String name;

	private TaxType type;

	private double value;

	private PriceType valueType;

}
