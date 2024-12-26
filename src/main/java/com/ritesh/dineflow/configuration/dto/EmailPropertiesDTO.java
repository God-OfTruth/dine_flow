package com.ritesh.dineflow.configuration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class EmailPropertiesDTO {

	private String host;

	private Integer port;

	private String userName;

	private String password;

	private String transferProtocol;

	private Boolean enableStartTls;

	private Boolean startTlsRequired;

}
