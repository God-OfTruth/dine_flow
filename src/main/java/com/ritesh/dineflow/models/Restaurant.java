package com.ritesh.dineflow.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "restaurant")
public class Restaurant {
	@Id
	private String id;

	private String name;

	private String tagLine;

	private String description;

	private String ownerId;

	private List<String> menuIds;

	private List<String> managers;

	private List<String> staffs;

	private List<String> mediaIds;

	private Address address;

}
