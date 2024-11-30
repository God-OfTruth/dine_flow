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
@Document(collection = "menus")
public class Menu {

	@Id
	private String id;

	private String name;

	private String description;

	private List<String> items;

}
