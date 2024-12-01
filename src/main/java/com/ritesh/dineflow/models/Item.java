package com.ritesh.dineflow.models;

import com.ritesh.dineflow.enums.Tag;
import com.ritesh.dineflow.enums.TaxType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "items")
public class Item {
	@Id
	private String id;

	private String name;

	private String description;

	private List<Tag> tags;

	private String mainMediaId;

	private List<String> mediaIds;

	private Price basePrice;

	private Map<TaxType, Tax> taxes;

}