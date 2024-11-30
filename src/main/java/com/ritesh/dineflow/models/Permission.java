package com.ritesh.dineflow.models;

import com.ritesh.dineflow.enums.UserPermission;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "permissions")
public class Permission extends  AbstractAuditingEntity{
	@Id
	private String id;

	private UserPermission permission;

}
