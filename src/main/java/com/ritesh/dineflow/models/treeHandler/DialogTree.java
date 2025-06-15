package com.ritesh.dineflow.models.treeHandler;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DialogTree {
	private Dialog dialog;
	private List<String> next;
}
