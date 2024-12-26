package com.ritesh.dineflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.thymeleaf.context.Context;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailInfo {

	private String from;

	private List<String> to;

	private String subject;

	private String body;

	private Boolean html;

	private String templateName;

	private Context context;

}
