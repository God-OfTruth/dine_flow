package com.ritesh.dineflow.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "categories")
public class Categories extends AbstractAuditingEntity {

	@Id
	private String id;

	private String name;

	private String description;

	private Set<String> restaurantIds;

	private List<Item> items;

	private String mediaId;

	@Builder.Default
	private boolean active = false;
}
