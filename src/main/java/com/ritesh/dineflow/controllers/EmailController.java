package com.ritesh.dineflow.controllers;

import com.ritesh.dineflow.dto.EmailInfo;
import com.ritesh.dineflow.services.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Email Controller")
@RestController
@RequestMapping(path = "/api/email")
public class EmailController {

	@Autowired
	public EmailService emailService;

	@PostMapping()
	ResponseEntity<Void> sendMail(@RequestBody EmailInfo emailInfo) {
		emailService.sendMail(emailInfo, null);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
