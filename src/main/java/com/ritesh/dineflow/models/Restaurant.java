package com.ritesh.dineflow.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "restaurant")
@Schema(name = "Restaurant")
public class Restaurant {

	@Id
	@Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private String id;

	@Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
	private String name;

	@Schema(name = "tagLine", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private String tagLine;

	@Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private String description;

	@Schema(name = "ownerId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private String ownerId;

	@Schema(name = "menuIds", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@Builder.Default
	private List<String> menuIds = new ArrayList<>();

	@Schema(name = "managers", description = "Email Ids of the Managers", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@Builder.Default
	private List<String> managers = new ArrayList<>();

	@Schema(name = "staffs", description = "Email Ids of the Staffs", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@Builder.Default
	private List<String> staffs = new ArrayList<>();

	@Schema(name = "mediaIds", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@Builder.Default
	private List<String> mediaIds = new ArrayList<>();

	@Schema(name = "address", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private Address address;

}
