package com.ritesh.dineflow.models;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {
	private String id;

	@NonNull
	private String token;
	@NonNull
	private Instant expiry;
	@NonNull
	private String userName;

}
