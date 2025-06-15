package com.ritesh.dineflow.models.treeHandler;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "story")
public class Story {
	@Id
	private String id;
	@NonNull
	private String title;
	private String description;
	private List<String> characters;
	private String owner;
	private List<String> thumbnails;
	private List<String> tags;
	private List<DialogTree> dialogTree;
	private Map<String, String> meta = new HashMap<>();
}
