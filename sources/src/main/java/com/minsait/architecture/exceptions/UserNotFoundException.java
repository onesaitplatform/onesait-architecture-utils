package com.minsait.architecture.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4033388050087870714L;

	public UserNotFoundException(final String message) {
		super(message);
	}
}
