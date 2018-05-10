package com.guoys.platform.commons.reflect;
public final class ReflectionAccessException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;


	public ReflectionAccessException(String message) {
		super(message);
	}

	public ReflectionAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReflectionAccessException(Throwable cause) {
		super(cause);
	}

}