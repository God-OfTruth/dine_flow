package com.ritesh.dineflow.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class Public {
	@GetMapping("/ping")
	public String ping(){
		return "pong";
	}
}
