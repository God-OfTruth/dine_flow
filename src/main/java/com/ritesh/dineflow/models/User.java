package com.ritesh.dineflow.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User extends AbstractAuditingEntity implements UserDetails {
	@Id
	private String id;

	private String username;

	private String email;

	@JsonIgnore
	private String password;

	private String profileId;

	@JsonIgnore
	@Builder.Default
	private Set<Role> roles = new HashSet<>();

	private String mobileNumber;

	@Builder.Default
	private boolean isExternalAccount = false;

	@Builder.Default
	private Boolean enabled = false;

	private String oauthId;  // OAuth ID from the provider (Google, Facebook, etc.)

	@JsonIgnore
	private String oauthProvider; // Name of the OAuth provider (e.g., "google", "facebook", "etc.")

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@SuppressWarnings("UnnecessaryLocalVariable")
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.flatMap(role -> {
					Stream<GrantedAuthority> roleAndPermissions = Stream.concat(
							Stream.of((GrantedAuthority) () -> "ROLE_" + role.getRole()), // Add ROLE_ prefix
							role.getPermissions().stream().map(permission ->
									(GrantedAuthority) () -> String.valueOf(permission.getPermission())) // Add permissions
					);
					return roleAndPermissions;
				})
				.collect(Collectors.toSet());
	}
}
