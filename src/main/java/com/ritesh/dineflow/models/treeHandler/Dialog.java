package com.ritesh.dineflow.models.treeHandler;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dialog {
	private String id;
	private String speaker;
	private List<String> characters;
	private String dialog;
	private String note;
	private List<String> tags;
	private Map<String, String> meta = new HashMap<>();
}
