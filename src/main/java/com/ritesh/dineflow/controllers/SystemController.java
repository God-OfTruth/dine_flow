package com.ritesh.dineflow.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "System Controller", description = "Will Contain all Endpoints Related to System")
@RestController
@RequestMapping("/api/system")
public class SystemController {

	@GetMapping("/ping")
	public String ping(){
		return "pong";
	}
}
