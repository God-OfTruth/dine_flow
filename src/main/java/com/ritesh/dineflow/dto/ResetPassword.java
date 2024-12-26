package com.ritesh.dineflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResetPassword {
	private String email;
	private String username;
	private String oldPassword;
	private String newPassword;
}
