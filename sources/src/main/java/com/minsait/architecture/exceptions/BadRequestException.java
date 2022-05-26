package com.minsait.architecture.exceptions;

public class BadRequestException extends OnesaitException {

	private static final long serialVersionUID = 1189902287827078847L;

	public BadRequestException(String key, Throwable t) {
		super(key, t);
	}

	public BadRequestException(String key) {
		super(key);
	}

}
