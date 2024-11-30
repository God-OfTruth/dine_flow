package com.ritesh.dineflow.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({AuthenticationException.class})
	public ResponseEntity<Object> onAuthenticationException(AuthenticationException exception){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
	}

	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity<Object> onUserNotFoundException(UserNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}

	@ExceptionHandler({UnauthorizedAccessException.class})
	public ResponseEntity<Object> onUnauthorisedException(UnauthorizedAccessException exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
	}

	@ExceptionHandler({ResourceAlreadyPresentException.class})
	public ResponseEntity<Object> onResourcePresentException(ResourceAlreadyPresentException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
	}

	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<Object> onResourceNotFoundException(ResourceNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}
}
