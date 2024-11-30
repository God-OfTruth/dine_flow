package com.ritesh.dineflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Authentication Request")
public class AuthenticationRequest {
	@Schema(name = "username", example = "varta_user", requiredMode = Schema.RequiredMode.REQUIRED)
	private String username;
	@Schema(name = "password", example = "Test@123", requiredMode = Schema.RequiredMode.REQUIRED)
	private String password;
}
