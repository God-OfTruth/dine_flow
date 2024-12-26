package com.ritesh.dineflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ForgotPassword {
	private String email;
	private String username;
}
