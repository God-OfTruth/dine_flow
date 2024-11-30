package com.ritesh.dineflow.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user_profile")
public class UserProfile {

	@Id
	private String id;
	@Schema(name = "firstName", example = "Varta", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private String firstName;

	@Schema(name = "lastName", example = "", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private String lastName;

	@Schema(name = "age", example = "18", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private int age;

	@Schema(name = "phoneNumber", example = "1234567891", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private String phoneNumber;

	@Schema(name = "address", example = "Varta, Varta,000000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private String address;

}
