package com.ritesh.dineflow.models.treeHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoryNode {
	private String id;
	private String name;
	private List<StoryNode> children;
	private Dialog dialog;
}
