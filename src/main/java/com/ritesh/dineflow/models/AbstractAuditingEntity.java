package com.ritesh.dineflow.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
public abstract class AbstractAuditingEntity {

	@CreatedBy
	@Field("created_by")
	@JsonIgnore
	private String createdBy;

	@CreatedDate
	@Field("created_date")
	private LocalDateTime createdDate = LocalDateTime.now();

	@LastModifiedBy
	@Field("last_modified_by")
	@JsonIgnore
	private String lastModifiedBy;

	@LastModifiedDate
	@Field("last_modified_date")
	private LocalDateTime lastModifiedDate = LocalDateTime.now();
}
