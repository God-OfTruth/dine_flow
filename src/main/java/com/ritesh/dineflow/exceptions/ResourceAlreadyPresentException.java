package com.ritesh.dineflow.exceptions;

public class ResourceAlreadyPresentException extends RuntimeException {
	public ResourceAlreadyPresentException(String message) {
		super(message);
	}
}
