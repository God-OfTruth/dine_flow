package com.ritesh.dineflow.exceptions;

public class InvalidRequestException extends RuntimeException {
	public InvalidRequestException(String message){
		super(message);
	}
}
