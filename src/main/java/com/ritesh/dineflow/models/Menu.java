package com.ritesh.dineflow.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "menus")
public class Menu extends AbstractAuditingEntity{

	@Id
	private String id;

	private String name;

	private String description;

	private Set<String> restaurantIds;

	private List<Item> items;

	@Builder.Default
	private boolean active = false;

}
