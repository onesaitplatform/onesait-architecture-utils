package com.minsait.architecture.exceptions;

public class OnesaitException extends RuntimeException {

	private static final long serialVersionUID = 6573988103782805628L;

	public OnesaitException(String key, Throwable t) {
		super(key, t);
	}

	public OnesaitException(String key) {
		super(key);
	}

}
