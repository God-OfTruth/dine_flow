package com.ritesh.dineflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Register Request")
public class RegistrationRequest {
	@Schema(name = "username", example = "varta_user", requiredMode = Schema.RequiredMode.REQUIRED)
	private String username;
	@Schema(name = "email", example = "contact@varta.com", requiredMode = Schema.RequiredMode.REQUIRED)
	private String email;
	@Schema(name = "password", example = "Test@123", requiredMode = Schema.RequiredMode.REQUIRED)
	private String password;
}
