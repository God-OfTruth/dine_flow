package com.ritesh.dineflow.models;

import com.ritesh.dineflow.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "roles")
public class Role extends AbstractAuditingEntity {
	@Id
	private String id;
	private UserRole role;
	private String description;

	private Set<Permission> permissions;
}
