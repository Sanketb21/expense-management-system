package com.splitwiseClone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus tells Spring to automatically set the http status code to 409 (conflict) whenever this exception is thrown
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User with this email already exists")
public class UserAlreadyExistsException extends RuntimeException{

	public UserAlreadyExistsException(String message) {
		super(message);
	}
	
}
