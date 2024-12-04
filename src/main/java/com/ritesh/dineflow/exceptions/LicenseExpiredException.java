package com.ritesh.dineflow.exceptions;

public class LicenseExpiredException extends RuntimeException {
	LicenseExpiredException(String message) {
		super(message);
	}
}
