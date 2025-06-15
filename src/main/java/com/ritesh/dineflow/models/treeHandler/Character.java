package com.ritesh.dineflow.models.treeHandler;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "character")
public class Character {
	@Id
	private String id;
	private String name;
	private String description;
	private List<String> thumbnails;
	private List<String> tags;
	private Map<String, String> meta = new HashMap<>();
}
