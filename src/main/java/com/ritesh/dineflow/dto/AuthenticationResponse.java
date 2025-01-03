package com.ritesh.dineflow.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
	private String accessToken;
	private String refreshToken;
}
