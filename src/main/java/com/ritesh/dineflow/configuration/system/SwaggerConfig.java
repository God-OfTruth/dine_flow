package com.ritesh.dineflow.configuration.system;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
	@Bean
	public GroupedOpenApi groupedOpenApi() {
		return GroupedOpenApi.builder()
				.group("public")
				.displayName("Public Apis")
				.pathsToMatch("/api/public/**")
				.build();
	}

	@Bean
	public GroupedOpenApi groupedPrivateApis() {
		return GroupedOpenApi.builder()
				.group("private")
				.displayName("Private Apis")
				.pathsToMatch("/api/**")
				.pathsToExclude("/api/public/**")
				.build();
	}


	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI(
				SpecVersion.V31
		).
				components(new Components()
						.addSecuritySchemes("bearer-jwt", new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
								.in(SecurityScheme.In.HEADER).name("Authorization"))

				).
				info(new Info()
						.contact(new Contact()
								.email("contact@varta.in")
								.name("Varta Support")))
				.addSecurityItem(
						new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write"))
				);
	}
}
